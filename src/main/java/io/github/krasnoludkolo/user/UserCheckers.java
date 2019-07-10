package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.resolver.Condition;
import io.github.krasnoludkolo.resolver.Success;
import io.github.krasnoludkolo.user.api.UserActionError;

public class UserCheckers {

    private InMemoryRepository<User> repository;

    UserCheckers(InMemoryRepository<User> repository) {
        this.repository = repository;
    }

    public Condition<UserActionError> isUserAdmin(int userId) {
        return () -> repository
                .findOne(userId)
                .filter(user -> user.isAdmin)
                .toEither(UserActionError.USER_IS_NOT_ADMIN)
                .map(Success::new);
    }

    public Condition<UserActionError> userExists(int userId) {
        return () -> repository
                .findOne(userId)
                .toEither(UserActionError.USER_NOT_FOUND)
                .map(Success::new);
    }


}
