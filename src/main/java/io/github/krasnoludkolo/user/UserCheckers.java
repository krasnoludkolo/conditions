package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.resolver.Condition;
import io.github.krasnoludkolo.resolver.SomeError;
import io.github.krasnoludkolo.resolver.Success;
import io.github.krasnoludkolo.user.api.UserError;
import io.vavr.collection.List;

public class UserCheckers {

    private InMemoryRepository<User> repository;

    UserCheckers(InMemoryRepository<User> repository) {
        this.repository = repository;
    }

    public Condition<UserError> isUserAdmin(int userId) {
        return () -> repository
                .findOne(userId)
                .filter(user -> user.isAdmin)
                .toEither(UserError.USER_IS_NOT_ADMIN)
                .map(Success::new);
    }

    public Condition<UserError> userExists(int userId) {
        return () -> repository
                .findOne(userId)
                .toEither(UserError.USER_NOT_FOUND)
                .map(Success::new);
    }


}
