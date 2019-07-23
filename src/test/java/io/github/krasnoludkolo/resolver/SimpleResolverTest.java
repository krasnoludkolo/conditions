package io.github.krasnoludkolo.resolver;

import io.vavr.control.Either;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleResolverTest {

    private final static Action<Integer> RETURN_ONE = ()-> 1;
    private final static Condition<Error> TRUE = ()-> Either.right(new Success());
    private final static Condition<Error> FALSE = ()-> Either.left(new Error());

    @Test
    public void  shouldDoActionWithoutConditions(){
        int result = Resolver.perform(RETURN_ONE);

        assertEquals(1,result);
    }

    @Test
    public void  shouldDoActionWithTrueCondition(){
        int result = Resolver
                .when(TRUE)
                .perform(RETURN_ONE)
                .get();

        assertEquals(1,result);
    }

    @Test
    public void  shouldReturnErrorWithFalseCondition(){
        Either<Error, Integer> result = Resolver
                .when(FALSE)
                .perform(RETURN_ONE);

        assertTrue(result.isLeft());
    }

    @Test
    public void  shouldDoActionWithAllTrueConditions(){
        int result = Resolver
                .when(
                        TRUE,
                        TRUE
                )
                .perform(RETURN_ONE)
                .get();

        assertEquals(1,result);
    }

    @Test
    public void  shouldReturnErrorWithFalseConditions(){
        Either<Error, Integer> result = Resolver
                .when(
                        FALSE,
                        TRUE
                )
                .perform(RETURN_ONE);

        assertTrue(result.isLeft());
    }


    private static class Error{}
}