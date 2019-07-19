package io.github.krasnoludkolo.auth;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.user.UserFacade;

public final class AuthConfiguration {

    public final AuthFacade authFacade;
    public final AuthenticationCheckers authenticationCheckers;

    static AuthConfiguration inMemory(UserFacade userFacade){
        Repository<AuthUser> repository = new InMemoryRepository<>();
        return new AuthConfiguration(userFacade, repository);
    }

    private AuthConfiguration(UserFacade userFacade, Repository<AuthUser> repository) {
        PasswordEncrypt passwordEncrypt = new PlainTextPasswordEncrypt();
        TokenGenerator tokenGenerator = new TokenGenerator();
        this.authenticationCheckers = new AuthenticationCheckers(repository, passwordEncrypt, tokenGenerator);
        this.authFacade = new AuthFacade(userFacade, authenticationCheckers);
    }
}
