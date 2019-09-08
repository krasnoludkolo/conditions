package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.infrastructure.ESRepository;
import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.user.UserCheckers;

import java.util.Random;

public final class GameConfiguration {

    public final GameFacade gameFacade;
    public final GameCheckers gameCheckers;
    public final GameRestController gameRestController;

    public static GameConfiguration inMemory(PointFacade pointFacade, UserCheckers userCheckers) {
        Repository<Game> repository = new InMemoryRepository<>();
        Random random = new Random();
        return new GameConfiguration(pointFacade, userCheckers, repository, random);
    }

    public static GameConfiguration withEs(PointFacade pointFacade, UserCheckers userCheckers) {
        Repository<Game> repository = new ESRepository<>(Game.class);
        Random random = new Random();
        return new GameConfiguration(pointFacade, userCheckers, repository, random);
    }

    public static GameConfiguration inMemory(PointFacade pointFacade, UserCheckers userCheckers, Random random) {
        Repository<Game> repository = new InMemoryRepository<>();
        return new GameConfiguration(pointFacade, userCheckers, repository, random);
    }

    private GameConfiguration(PointFacade pointFacade, UserCheckers userCheckers, Repository<Game> repository, Random random) {
        this.gameCheckers = new GameCheckers(repository);
        GameService gameService = new GameService(repository, pointFacade,random);
        this.gameFacade = new GameFacade(userCheckers, gameCheckers, gameService);
        this.gameRestController = new GameRestController(gameFacade);
    }

}
