package io.github.krasnoludkolo.infrastructure.http;

import io.github.krasnoludkolo.infrastructure.ActionError;
import io.javalin.Context;
import io.vavr.control.Either;
import io.vavr.control.Option;

public final class ResponseResolver {

    private ResponseResolver() {
    }

    public static Context resolve(Either<? extends ActionError, ?> input, Context ctx) {
        return input
                .map(ctx::json)
                .getOrElseGet(error -> createErrorResponse(error, ctx));
    }

    private static Context createErrorResponse(ActionError error,Context ctx) {
        return ctx
                .json(new ErrorResponse(error.getMessage()))
                .status(error.getCode());
    }

    public static <T> Context resolve(Option<T> input, Context ctx) {
        return input
                .map(ctx::json)
                .getOrElse(ctx.status(404));
    }


}
