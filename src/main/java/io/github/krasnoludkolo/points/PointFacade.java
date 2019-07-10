package io.github.krasnoludkolo.points;

import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.resolver.Resolver;
import io.github.krasnoludkolo.resolver.Success;
import io.github.krasnoludkolo.user.UserCheckers;
import io.github.krasnoludkolo.user.api.UserActionError;
import io.vavr.collection.List;
import io.vavr.control.Either;

public class PointFacade {

    private UserCheckers userCheckers;
    private PointsService pointsService;

    PointFacade(UserCheckers userCheckers, InMemoryRepository<Point> repository) {
        this.userCheckers = userCheckers;
        this.pointsService = new PointsService(repository);
    }

    public Either<? extends ActionError,Success> addPointToUser(int userId){
        return Resolver
                .when(
                        userCheckers.userExists(userId)
                )
                .perform(
                        pointsService.addPointToUser(userId)
                );
    }

    public Either<? extends ActionError,Success> setUserResult(int userId, int points, int adminId){
        return Resolver
                .when(
                        userCheckers.userExists(userId),
                        userCheckers.isUserAdmin(adminId)
                )
                .perform(
                        pointsService.setUserPoints(userId,points)
                );
    }

    public Either<? extends ActionError,Integer> getUserPoints(int userId){
        return Resolver
                .when(
                        userCheckers.userExists(userId)
                )
                .perform(
                        pointsService.getUserPoints(userId)
                );
    }


}
