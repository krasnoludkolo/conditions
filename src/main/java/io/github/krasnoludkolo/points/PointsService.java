package io.github.krasnoludkolo.points;

import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.resolver.Action;
import io.github.krasnoludkolo.resolver.Success;
import io.vavr.control.Option;

final class PointsService {

    private Repository<Point> repository;

    PointsService(Repository<Point> repository) {
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
                .findOne(userId)
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

    Action<Integer> createResultForUser(int id) {
        return ()-> Option.of(Point.create(id))
                .map(repository::save)
                .map(Point::getId)
                .get();

    }
}
