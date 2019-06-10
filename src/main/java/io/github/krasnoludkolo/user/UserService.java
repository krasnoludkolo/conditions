package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.resolver.Action;
import io.github.krasnoludkolo.resolver.Success;

final class UserService {

    private InMemoryRepository<User> repository;

    UserService(InMemoryRepository<User> repository) {
        this.repository = repository;
    }

    Action<Success> promoteToAdmin(int userId) {
        return () -> repository
                .findOne(userId)
                .map(User::promoteToAdmin)
                .map(this::updateUser)
                .get();
    }

    private Success updateUser(User user) {
        return repository.update(user.id, user);
    }
}
