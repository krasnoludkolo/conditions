package io.github.krasnoludkolo.auth;

import io.github.krasnoludkolo.auth.api.ApiToken;
import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.resolver.Resolver;
import io.github.krasnoludkolo.user.UserFacade;
import io.vavr.control.Either;

public final class AuthFacade {

    private final UserFacade userFacade;
    private final AuthenticationCheckers authenticationCheckers;
    private final TokenGenerator tokenGenerator = new TokenGenerator();

    AuthFacade(UserFacade userFacade, AuthenticationCheckers authenticationCheckers) {
        this.userFacade = userFacade;
        this.authenticationCheckers = authenticationCheckers;
    }

    public Either<ActionError, ApiToken> login(int id, String password) {
        return Resolver
                .when(
                        authenticationCheckers.correctPassword(id, password)
                ).perform(
                        tokenGenerator.generate(id)
                );
    }

    public Either<ActionError, ApiToken> register(String password) {
        //TODO
        return Either.right(new ApiToken("" + 1));
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
