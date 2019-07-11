package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.resolver.Action;
import io.github.krasnoludkolo.resolver.Success;
import io.github.krasnoludkolo.user.api.UserDTO;

final class UserService {

    private Repository<User> repository;
    private PointFacade pointFacade;

    UserService(Repository<User> repository, PointFacade pointFacade) {
        this.repository = repository;
        this.pointFacade = pointFacade;
    }

    Action<Success> promoteToAdmin(int userId) {
        return () -> repository
                .findOne(userId)
                .map(User::promoteToAdmin)
                .map(repository::update)
                .get();
    }

    Action<UserDTO> createUser() {
        return () -> {
            User user = User.createNormal();
            pointFacade.createResultForUser(user.getId());
            return repository
                    .save(user)
                    .toDTO();
        };
    }

    Action<UserDTO> getUserInfo(int id) {
        return () -> repository
                .findOne(id)
                .map(User::toDTO)
                .get();
    }
}
