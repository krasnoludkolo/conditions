package io.github.krasnoludkolo.resolver;

import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.control.Either;

public final class Resolver {

    private Resolver() {
    }

    private static <T> Either<List<SomeError>, T> perform(Action<T> action, Condition... conditions) {
        return checkConditions(conditions)
                .flatMap((Success s) -> action.perform());
    }

    private static Either<List<SomeError>, Success> checkConditions(Condition[] conditions) {
        return Either.sequence(
                Array.of(conditions)
                        .toList()
                        .map(Condition::test)
        ).mapLeft(errors->errors.flatMap(i->i).toList())
                .map(Success::new);
    }

    public static ResolverBuilder when(Condition... conditions) {
        return new ResolverBuilder(conditions, ()->Either.left(List.of(new SomeError())));
    }


    public static class ResolverBuilder {
        Condition[] conditions;
        Action action;

        private ResolverBuilder(Condition[] conditions, Action action) {
            this.conditions = conditions;
            this.action = action;
        }

        public <T> Either<List<SomeError>, T> perform(Action<T> action) {
            return Resolver.perform(action, conditions);
        }

    }

}
