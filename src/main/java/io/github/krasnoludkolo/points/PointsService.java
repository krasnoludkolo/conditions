package io.github.krasnoludkolo.points;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.resolver.Action;
import io.github.krasnoludkolo.resolver.Success;

final class PointsService {

    private InMemoryRepository<Point> repository;

    PointsService(InMemoryRepository<Point> repository) {
        this.repository = repository;
    }

    Action<Success> addPointToUser(int userId) {
        return ()->repository
                .findOne(userId)
                .map(Point::increase)
                .map(this::updatePoint)
                .get();
    }

    Action<Success> subtractPointToUser(int userId) {
        return ()->repository
                .findOne(userId)
                .map(Point::decrease)
                .map(this::updatePoint)
                .get();
    }

    private Success updatePoint(Point point) {
        repository.update(point.userId,point);
        return new Success();
    }


}
