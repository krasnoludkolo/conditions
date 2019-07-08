package io.github.krasnoludkolo.points;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.user.UserCheckers;

public final class PointConfiguration {

    private PointFacade pointFacade;

    public static PointConfiguration inMemory(UserCheckers userCheckers){
        return new PointConfiguration(userCheckers);
    }

    private PointConfiguration(UserCheckers userCheckers) {
        InMemoryRepository<Point> repository = new InMemoryRepository<>();
        this.pointFacade = new PointFacade(userCheckers,repository);
    }

    public PointFacade getPointFacade() {
        return pointFacade;
    }
}
