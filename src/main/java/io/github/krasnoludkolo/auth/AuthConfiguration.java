package io.github.krasnoludkolo.auth;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.user.UserFacade;

public final class AuthConfiguration {

    public final AuthFacade authFacade;
    public final AuthenticationCheckers authenticationCheckers;
    public final LoginRestController loginRestController;
    public final TokenAccessManager tokenAccessManager;

    public static AuthConfiguration inMemory(UserFacade userFacade){
        Repository<AuthUser> repository = new InMemoryRepository<>();
        return new AuthConfiguration(userFacade, repository);
    }

    private AuthConfiguration(UserFacade userFacade, Repository<AuthUser> repository) {

        PasswordEncrypt passwordEncrypt = new PlainTextPasswordEncrypt();
        TokenGenerator tokenGenerator = new TokenGenerator();
        Registration registration = new Registration(repository, passwordEncrypt, userFacade);
        this.authenticationCheckers = new AuthenticationCheckers(repository, passwordEncrypt, tokenGenerator);
        this.authFacade = new AuthFacade(authenticationCheckers, registration);
        this.loginRestController = new LoginRestController(authFacade);
        this.tokenAccessManager = new TokenAccessManager(authFacade);
    }
}
