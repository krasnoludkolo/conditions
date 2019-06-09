package io.github.krasnoludkolo.user.api;

import io.github.krasnoludkolo.infrastructure.ErrorResponse;

public enum UserError implements ErrorResponse {
    USER_IS_NOT_ADMIN("User is not admin",400),
    USER_NOT_FOUND("User not found",404);

    private String message;
    private int code;

    UserError(String message, int code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }
}