package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.resolver.Condition;
import io.github.krasnoludkolo.resolver.Success;
import io.github.krasnoludkolo.user.api.UserActionError;

public class UserCheckers {

    private Repository<User> repository;

    UserCheckers(Repository<User> repository) {
        this.repository = repository;
    }

    public Condition<ActionError> isUserAdmin(int userId) {
        return () -> repository
                .findOne(userId)
                .filter(user -> user.isAdmin)
                .toEither((ActionError)UserActionError.USER_IS_NOT_ADMIN)
                .map(Success::new);
    }

    public Condition<ActionError> userExists(int userId) {
        return () -> repository
                .findOne(userId)
                .toEither((ActionError)UserActionError.USER_NOT_FOUND)
                .map(Success::new);
    }


}
