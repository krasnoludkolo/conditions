package io.github.krasnoludkolo.auth;

import io.github.krasnoludkolo.auth.api.ApiToken;
import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.resolver.Condition;
import io.github.krasnoludkolo.resolver.Success;
import io.github.krasnoludkolo.user.api.UserActionError;
import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

final class AuthenticationCheckers {

    private final Repository<AuthUser> repository;
    private final PasswordEncrypt passwordEncrypt;
    private final TokenGenerator tokenGenerator;

    AuthenticationCheckers(Repository<AuthUser> repository, PasswordEncrypt passwordEncrypt, TokenGenerator tokenGenerator) {
        this.repository = repository;
        this.passwordEncrypt = passwordEncrypt;
        this.tokenGenerator = tokenGenerator;
    }

    Condition<ActionError> correctPassword(int id, String password) {
        return () -> repository
                .findOne(id)
                .map(authUser -> passwordEncrypt.checkPassword(password, authUser.password))
                .toEither((ActionError) UserActionError.USER_NOT_FOUND)
                .flatMap(this::booleanToEither);
    }

    @NotNull
    private Either<ActionError,Success> booleanToEither(Boolean value) {
        return value ? Either.right(new Success()) : Either.left(UserActionError.WRONG_PASSWORD);
    }

    Condition<ActionError> isTokenValid(ApiToken token){
        return ()->tokenGenerator.isTokenValid(token);
    }

}
