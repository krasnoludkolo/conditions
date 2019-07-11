package io.github.krasnoludkolo.points;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.infrastructure.Repository;

public final class PointConfiguration {

    private final PointCheckers pointCheckers;
    private final PointFacade pointFacade;

    public static PointConfiguration inMemory(){
        Repository<Point> repository = new InMemoryRepository<>();
        return new PointConfiguration(repository);
    }

    private PointConfiguration(Repository<Point> repository) {
        this.pointCheckers = new PointCheckers(repository);
        this.pointFacade = new PointFacade(repository, pointCheckers);
    }

    public PointFacade getPointFacade() {
        return pointFacade;
    }

    public PointCheckers getPointCheckers() {
        return pointCheckers;
    }
}
