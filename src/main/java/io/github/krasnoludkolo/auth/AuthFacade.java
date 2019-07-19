package io.github.krasnoludkolo.auth;

import io.github.krasnoludkolo.auth.api.ApiToken;
import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.resolver.Resolver;
import io.vavr.control.Either;

public final class AuthFacade {

    private final AuthenticationCheckers authenticationCheckers;
    private final TokenGenerator tokenGenerator = new TokenGenerator();
    private final Registration registration;

    AuthFacade(AuthenticationCheckers authenticationCheckers, Registration registration) {
        this.authenticationCheckers = authenticationCheckers;
        this.registration = registration;
    }

    public Either<ActionError, ApiToken> login(int id, String password) {
        return Resolver
                .when(
                        authenticationCheckers.correctPassword(id, password)
                ).perform(
                        tokenGenerator.generate(id)
                );
    }

    public ApiToken register(String password) {
        AuthUser register = registration.register(password);
        return tokenGenerator.generate(register.getId()).perform();
    }

    public Either<ActionError, Integer> getIdFromToken(ApiToken token) {
        return Resolver
                .when(
                        authenticationCheckers.isTokenValid(token)
                ).perform(
                        tokenGenerator.getIdFromToken(token)
                );
    }


}
