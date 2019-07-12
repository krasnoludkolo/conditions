package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.BetDTO;
import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.resolver.Resolver;
import io.github.krasnoludkolo.user.UserCheckers;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;

public final class GameFacade {

    private final UserCheckers userCheckers;
    private final GameCheckers gameCheckers;
    private final GameService gameService;

    GameFacade(UserCheckers userCheckers, GameCheckers gameCheckers, GameService gameService) {
        this.userCheckers = userCheckers;
        this.gameCheckers = gameCheckers;
        this.gameService = gameService;
    }

    public Either<? extends ActionError, GameDTO> createGame(int maxNumber){
        return Resolver
                .when(
                        gameCheckers.isMaxNuberValid(maxNumber)
                ).perform(
                        gameService.createGame(maxNumber)
                );
    }

    public List<GameDTO> getAllGames() {
        return gameService.getAllGames();
    }

    public Either<? extends ActionError, GameDTO> addBet(BetDTO bet) {
        return Resolver
                .when(
                        userCheckers.userExists(bet.userId),
                        gameCheckers.gameExists(bet.gameId)
                ).perform(
                        gameService.addBet(bet)
                );
    }

    public Either<? extends ActionError, GameDTO> endGame(int id) {
        return Resolver
                .when(
                        gameCheckers.gameExists(id)
                ).perform(
                        gameService.endGame(id)
                );
    }

    public Option<GameDTO> getGameById(int id){
        return gameService.getGameById(id);
    }

}
