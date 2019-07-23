package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.EndGameRequestDTO;
import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.game.api.NewBetDTO;
import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.resolver.Resolver;
import io.github.krasnoludkolo.user.UserCheckers;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;

import static io.github.krasnoludkolo.resolver.Resolver.and;
import static io.github.krasnoludkolo.resolver.Resolver.or;

public final class GameFacade {

    private final UserCheckers userCheckers;
    private final GameCheckers gameCheckers;
    private final GameService gameService;

    GameFacade(UserCheckers userCheckers, GameCheckers gameCheckers, GameService gameService) {
        this.userCheckers = userCheckers;
        this.gameCheckers = gameCheckers;
        this.gameService = gameService;
    }

    public Either<ActionError, GameDTO> createGame(int maxNumber, int gameCreatorId) {
        return Resolver
                .when(
                        gameCheckers.isMaxNumberValid(maxNumber),
                        userCheckers.userExists(gameCreatorId)
                ).perform(
                        gameService.createGame(maxNumber, gameCreatorId)
                );
    }

    public List<GameDTO> getAllGames() {
        return gameService.getAllGames();
    }

    public Either<ActionError, GameDTO> addBet(NewBetDTO bet) {
        return Resolver
                .when(
                        userCheckers.userExists(bet.userId),
                        gameCheckers.gameExists(bet.gameId),
                        gameCheckers.isBetPossible(bet.bet, bet.gameId)
                ).perform(
                        gameService.addBet(bet)
                );
    }

    public Either<ActionError, GameDTO> endGame(EndGameRequestDTO request) {
        return Resolver
                .when(
                        or(
                                and(
                                        gameCheckers.gameExists(request.getGameId()),
                                        gameCheckers.canEndGame(request.getGameId(), request.getUserId())
                                ),
                                and(
                                        userCheckers.isAdmin(request.getUserId())
                                )
                        )

                ).perform(
                        gameService.endGame(request.getGameId())
                );
    }

    public Option<GameDTO> getGameById(int id) {
        return gameService.getGameById(id);
    }

}
