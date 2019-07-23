package io.github.krasnoludkolo.resolver;

import io.vavr.control.Either;
import org.junit.Test;

import static io.github.krasnoludkolo.resolver.Resolver.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ComplexResolverTest {

    private final static Action<Integer> RETURN_ONE = () -> 1;
    private final static Condition<Error> TRUE = () -> Either.right(new Success());
    private final static Condition<Error> FALSE = () -> Either.left(new Error());

    @Test
    public void shouldDoActionWithTrueConditions() {
        int result = when(
                or(
                        TRUE,
                        FALSE
                )
        ).perform(RETURN_ONE).get();

        assertEquals(1, result);
    }

    @Test
    public void shouldDoActionWithTrueConditions2() {
        int result = when(
                and(
                        or(
                                TRUE,
                                FALSE
                        ),
                        and(
                                TRUE,
                                TRUE
                        )
                )
        ).perform(RETURN_ONE).get();

        assertEquals(1, result);
    }

    @Test
    public void shouldDoActionWithTrueConditions3() {
        int result = when(
                TRUE,
                TRUE
        ).or(
                TRUE
        ).perform(RETURN_ONE).get();

        assertEquals(1, result);
    }

    @Test
    public void shouldNotDoActionWithFalseConditions() {
        Either<Error, Integer> result = when(
                and(
                        TRUE,
                        FALSE
                )
        ).perform(RETURN_ONE);

        assertTrue(result.isLeft());
    }


    private static class Error {
    }
}