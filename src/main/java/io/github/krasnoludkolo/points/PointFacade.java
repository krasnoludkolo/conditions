package io.github.krasnoludkolo.points;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.resolver.Resolver;
import io.github.krasnoludkolo.resolver.SomeError;
import io.github.krasnoludkolo.resolver.Success;
import io.github.krasnoludkolo.user.UserCheckers;
import io.vavr.collection.List;
import io.vavr.control.Either;

public class PointFacade {

    private UserCheckers userCheckers;
    private PointsService pointsService;

    public PointFacade(UserCheckers userCheckers) {
        this.userCheckers = userCheckers;
        this.pointsService = new PointsService(new InMemoryRepository<>());
    }


    public Either<List<SomeError>,Success> addPointToUser(int userId){
        return Resolver
                .when(userCheckers.userExists(userId))
                .perform(pointsService.addPointToUser(userId));

    }


}
