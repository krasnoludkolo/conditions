package io.github.krasnoludkolo.resolver;

import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Either;

public final class Resolver {

    private Resolver() {
    }

    public static <T> T perform(Action<T> action) {
        return  action.perform();
    }

    private static <T, E> Either<List<E>, T> perform(Action<T> action, List<Condition<E>> conditions) {
        return checkConditions(conditions)
                .map(s -> action.perform());
    }

    private static <E> Either<List<E>, Success> checkConditions(List<Condition<E>> conditions) {
        return Either.sequence(
                Array.ofAll(conditions)
                        .toList()
                        .map(Condition::test)
        ).mapLeft(Seq::toList)
                .map(Success::new);
    }

    @SafeVarargs
    public static <E> ResolverBuilder<E> when(Condition<E>... conditions) {
        return when(List.of(conditions));
    }

    private static <E> ResolverBuilder<E> when(List<Condition<E>> conditions) {
        return new ResolverBuilder<>(conditions);
    }

    public static class ResolverBuilder<E> {
        List<Condition<E>> conditions;

        private ResolverBuilder(List<Condition<E>> conditions) {
            this.conditions = conditions;
        }

        public <T> Either<List<E>, T> perform(Action<T> action) {
            return Resolver.perform(action, conditions);
        }

    }

}
