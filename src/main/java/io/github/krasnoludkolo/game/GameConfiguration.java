package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.user.UserCheckers;

public final class GameConfiguration {

    private final GameFacade gameFacade;
    private final GameCheckers gameCheckers;

    public static GameConfiguration inMemory(PointFacade pointFacade, UserCheckers userCheckers){
        Repository<Game> repository = new InMemoryRepository<>();
        return new GameConfiguration(pointFacade, userCheckers, repository);
    }

    private GameConfiguration(PointFacade pointFacade, UserCheckers userCheckers, Repository<Game> repository) {
        this.gameCheckers = new GameCheckers(repository);
        this.gameFacade = new GameFacade(pointFacade, userCheckers, gameCheckers, repository);
    }

    public GameFacade getGameFacade() {
        return gameFacade;
    }

    public GameCheckers getGameCheckers() {
        return gameCheckers;
    }
}
