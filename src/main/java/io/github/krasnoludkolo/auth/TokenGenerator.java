package io.github.krasnoludkolo.auth;

import io.github.krasnoludkolo.auth.api.ApiToken;
import io.github.krasnoludkolo.auth.api.UserAuthError;
import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.resolver.Action;
import io.github.krasnoludkolo.resolver.Success;
import io.vavr.control.Either;
import io.vavr.control.Try;

final class TokenGenerator {

    Action<ApiToken> generate(int id){
        return ()->new ApiToken(String.valueOf(id));
    }

    Action<Integer> getIdFromToken(ApiToken token){
        return ()->Integer.parseInt(token.token);
    }

    public Either<ActionError, Success> isTokenValid(ApiToken token) {
        return Try.of(()->Integer.parseInt(token.token))
                .toEither()
                .map(Success::new)
                .mapLeft(x-> UserAuthError.INVALID_TOKEN);
    }
}
