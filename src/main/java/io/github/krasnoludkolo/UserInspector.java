package io.github.krasnoludkolo;

import io.github.krasnoludkolo.resolver.SomeError;
import io.github.krasnoludkolo.resolver.Success;
import io.vavr.collection.List;
import io.vavr.control.Either;

final class UserInspector {


    Either<List<SomeError>, Success> isAdmin(User user) {
        int id = user.getId();
        return id == 1? Either.right(new Success()):Either.left(List.of(new SomeError()));
    }


}
