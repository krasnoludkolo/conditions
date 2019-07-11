package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.Bet;
import io.github.krasnoludkolo.game.api.FinishedGameDTO;
import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.resolver.Resolver;
import io.github.krasnoludkolo.user.UserCheckers;
import io.vavr.collection.List;
import io.vavr.control.Either;

public final class GameFacade {

    private final PointFacade pointFacade;
    private final UserCheckers userCheckers;
    private final GameCheckers gameCheckers;
    private final Repository<Game> repository;
    private final GameService gameService;

    GameFacade(PointFacade pointFacade, UserCheckers userCheckers, GameCheckers gameCheckers, Repository<Game> repository) {
        this.pointFacade = pointFacade;
        this.userCheckers = userCheckers;
        this.gameCheckers = gameCheckers;
        this.repository = repository;
        this.gameService = new GameService(repository);
    }

    public List<GameDTO> getAllGames() {
        return repository
                .findAll()
                .map(Game::toDTO);
    }

    public Either<? extends ActionError, GameDTO> addBet(Bet bet) {
        return Resolver
                .when(
                        userCheckers.userExists(bet.userId),
                        gameCheckers.gameExists(bet.gameId)
                ).perform(
                        gameService.addBet(bet)
                ).map(Game::toDTO);
    }

    public Either<? extends ActionError, GameDTO> endGame(int id) {
        return Resolver
                .when(
                        gameCheckers.gameExists(id)
                ).perform(
                        gameService.endGame(id, pointFacade)
                ).map(Game::toDTO);
    }

    public GameDTO createGame(){
        return gameService.createGame();
    }

}
