package io.github.krasnoludkolo.points;

import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.resolver.Resolver;
import io.github.krasnoludkolo.resolver.Success;
import io.vavr.control.Either;

public class PointFacade {

    private final PointsService pointsService;
    private final PointCheckers pointCheckers;

    PointFacade(Repository<Point> repository, PointCheckers pointCheckers) {
        this.pointsService = new PointsService(repository);
        this.pointCheckers = pointCheckers;
    }

    public Either<? extends ActionError,Success> addPointToUser(int userId){
        return Resolver
                .when(
                        pointCheckers.userExists(userId)
                )
                .perform(
                        pointsService.addPointToUser(userId)
                );
    }

    public Either<? extends ActionError,Integer> getUserPoints(int userId){
        return Resolver
                .when(
                        pointCheckers.userExists(userId)
                )
                .perform(
                        pointsService.getUserPoints(userId)
                );
    }

    public Either<? extends ActionError, Integer> createResultForUser(int id){
        return Resolver
                .when(
                        pointCheckers.userNotExists(id)
                ).perform(
                        pointsService.createResultForUser(id)
        );
    }

}
