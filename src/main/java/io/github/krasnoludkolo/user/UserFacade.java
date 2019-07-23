package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.resolver.Resolver;
import io.github.krasnoludkolo.resolver.Success;
import io.github.krasnoludkolo.user.api.UserDTO;
import io.vavr.control.Either;

public class UserFacade {

    private UserService userService;
    private UserCheckers userCheckers;

    UserFacade(UserCheckers userCheckers, Repository<User> repository, PointFacade pointFacade) {
        this.userCheckers = userCheckers;
        this.userService = new UserService(repository, pointFacade);
    }

    public UserDTO createUserWithId(int id){
        return Resolver
                .perform(
                        userService.createUser(id)

                );
    }

    public UserDTO createAdminWithId(int id){
        return Resolver
                .perform(
                        userService.createAdmin(id)

                );
    }

    public Either<ActionError, UserDTO> getUserInfo(int id){
        return Resolver
                .when(
                        userCheckers.userExists(id)
                )
                .perform(
                        userService.getUserInfo(id)
                );
    }

    public Either<ActionError, Success> promoteToAdmin(int promoterId, int userId) {
        return Resolver
                .when(
                        userCheckers.userExists(promoterId),
                        userCheckers.isAdmin(promoterId),
                        userCheckers.userExists(userId)
                )
                .perform(
                        userService.promoteToAdmin(userId)
                );
    }

}
