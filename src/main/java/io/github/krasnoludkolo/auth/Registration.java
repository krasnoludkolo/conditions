package io.github.krasnoludkolo.auth;

import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.user.UserFacade;
import io.vavr.control.Option;

final class Registration {

    private final Repository<AuthUser> repository;
    private final PasswordEncrypt passwordEncrypt;
    private final UserFacade userFacade;

    Registration(Repository<AuthUser> repository, PasswordEncrypt passwordEncrypt, UserFacade userFacade) {
        this.repository = repository;
        this.passwordEncrypt = passwordEncrypt;
        this.userFacade = userFacade;
    }

    AuthUser register(String password){
        String encryptedPassword = passwordEncrypt.encryptPassword(password);
        return Option.of(AuthUser.create(encryptedPassword))
                .peek(authUser -> userFacade.createUserWithId(authUser.getId()))
                .map(repository::save)
                .get();
    }
}
