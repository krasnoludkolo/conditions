package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.resolver.Action;
import io.github.krasnoludkolo.resolver.Success;
import io.github.krasnoludkolo.user.api.UserDTO;

final class UserService {

    private InMemoryRepository<User> repository;

    UserService(InMemoryRepository<User> repository) {
        this.repository = repository;
    }

    Action<Success> promoteToAdmin(int userId) {
        return () -> repository
                .findOne(userId)
                .map(User::promoteToAdmin)
                .map(repository::update)
                .get();
    }

    Action<UserDTO> createUser() {
        return () -> repository
                .save(User.createNormal())
                .toDTO();
    }

    Action<UserDTO> getUserInfo(int id) {
        return () -> repository
                .findOne(id)
                .map(User::toDTO)
                .get();
    }
}
