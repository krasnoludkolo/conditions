package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.resolver.Resolver;
import io.github.krasnoludkolo.resolver.SomeError;
import io.github.krasnoludkolo.resolver.Success;
import io.github.krasnoludkolo.user.api.UserDTO;
import io.vavr.collection.List;
import io.vavr.control.Either;

public class UserFacade {

    private UserService userService;
    private UserCheckers userCheckers;

    public UserFacade(UserCheckers userCheckers, InMemoryRepository<User> repository) {
        this.userCheckers = userCheckers;
        this.userService = new UserService(repository);
    }

    UserDTO cerateUser(){
        return Resolver
                .perform(
                        userService.createUser()
                );
    }


    public Either<List<SomeError>, Success> promoteToAdmin(int promoterId, int userId) {
        return Resolver
                .when(
                        userCheckers.userExists(promoterId),
                        userCheckers.isUserAdmin(promoterId),
                        userCheckers.userExists(userId)
                )
                .perform(
                        userService.promoteToAdmin(userId)
                );
    }


}
