package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.resolver.Resolver;
import io.github.krasnoludkolo.resolver.Success;
import io.github.krasnoludkolo.user.api.UserDTO;
import io.github.krasnoludkolo.user.api.UserActionError;
import io.vavr.collection.List;
import io.vavr.control.Either;

public class UserFacade {

    private UserService userService;
    private UserCheckers userCheckers;

    UserFacade(UserCheckers userCheckers, InMemoryRepository<User> repository) {
        this.userCheckers = userCheckers;
        this.userService = new UserService(repository);
    }

    public UserDTO createUser(){
        return Resolver
                .perform(
                        userService.createUser()
                );
    }

    public Either<? extends ActionError, UserDTO> getUserInfo(int id){
        return Resolver
                .when(
                        userCheckers.userExists(id)
                )
                .perform(
                        userService.getUserInfo(id)
                );
    }

    public Either<? extends ActionError, Success> promoteToAdmin(int promoterId, int userId) {
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
