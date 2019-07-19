package io.github.krasnoludkolo.auth.api;

import io.github.krasnoludkolo.infrastructure.ActionError;

public enum UserAuthError implements ActionError {
    WRONG_PASSWORD("Wrong password",400),
    INVALID_TOKEN("Invalid token",400);

    private String message;
    private int code;

    UserAuthError(String message, int code) {
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