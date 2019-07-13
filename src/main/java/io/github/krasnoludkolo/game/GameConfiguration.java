package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.user.UserCheckers;

import java.util.Random;

public final class GameConfiguration {

    private final GameFacade gameFacade;
    private final GameCheckers gameCheckers;

    public static GameConfiguration inMemoryWithRandom(PointFacade pointFacade, UserCheckers userCheckers){
        Repository<Game> repository = new InMemoryRepository<>();
        Random random = new Random();
        return new GameConfiguration(pointFacade, userCheckers, repository, random);
    }

    public static GameConfiguration inMemoryWithRandom(PointFacade pointFacade, UserCheckers userCheckers, Random random){
        Repository<Game> repository = new InMemoryRepository<>();
        return new GameConfiguration(pointFacade, userCheckers, repository, random);
    }

    private GameConfiguration(PointFacade pointFacade, UserCheckers userCheckers, Repository<Game> repository, Random random) {
        this.gameCheckers = new GameCheckers(repository);
        GameService gameService = new GameService(repository, pointFacade,random);
        this.gameFacade = new GameFacade(userCheckers, gameCheckers, gameService);
    }

    public GameFacade getGameFacade() {
        return gameFacade;
    }

    public GameCheckers getGameCheckers() {
        return gameCheckers;
    }
}
