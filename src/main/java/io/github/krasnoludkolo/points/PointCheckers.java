package io.github.krasnoludkolo.points;

import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.points.api.PointsActionError;
import io.github.krasnoludkolo.resolver.Condition;
import io.github.krasnoludkolo.resolver.Success;

public class PointCheckers {

    private final Repository<Point> repository;

    PointCheckers(Repository<Point> repository) {
        this.repository = repository;
    }

    public Condition<PointsActionError> userNotExists(int userId) {
        return () -> repository
                .findOne(userId)
                .map(p->PointsActionError.USER_EXISTS)
                .toEither(Success::new)
                .swap();
    }

    public Condition<PointsActionError> userExists(int userId) {
        return () -> repository
                .findOne(userId)
                .toEither(PointsActionError.USER_NOT_FOUND)
                .map(Success::new);
    }
}
