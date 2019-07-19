package io.github.krasnoludkolo.infrastructure.http;

import io.github.krasnoludkolo.infrastructure.ActionError;
import io.javalin.Context;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class ResponseResolver {

    private ResponseResolver() {
    }

    public static Context resolve(Either<ActionError, ?> input, Context ctx) {
        return input
                .map(toJson(ctx))
                .getOrElseGet(error -> createErrorResponse(error, ctx));
    }

    @NotNull
    private static Function<Object, Context> toJson(Context ctx) {
        return o->ctx
                .json(o)
                .contentType("application/json");
    }

    private static Context createErrorResponse(ActionError error,Context ctx) {
        return ctx
                .json(new ServerResponse(error.getMessage()))
                .status(error.getCode());
    }

    public static <T> Context resolve(Option<T> input, Context ctx) {
        return input
                .map(toJson(ctx))
                .getOrElse(ctx.status(404));
    }

    public static Context resolve(Object input, Context ctx) {
        return toJson(ctx).apply(input);
    }


}
