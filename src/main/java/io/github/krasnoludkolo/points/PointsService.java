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
                .map(repository::update)
                .get();
    }

    Action<Success> subtractPointToUser(int userId) {
        return ()->repository
                .findOne(userId)
                .map(Point::decrease)
                .map(repository::update)
                .get();
    }

    Action<Success> setUserPoints(int userId, int points) {
        return () -> repository
                .findOne(points)
                .map(point -> point.setCount(points))
                .map(repository::update)
                .get();
    }

    Action<Integer> getUserPoints(int userId) {
        return ()->repository
                .findOne(userId)
                .map(p->p.points)
                .get();
    }

    private Success updatePoint(Point point) {
        return repository.update(point);
    }


}
