package io.github.krasnoludkolo.points.api;

import io.github.krasnoludkolo.infrastructure.ActionError;

public enum PointsActionError implements ActionError {
    USER_NOT_FOUND("user not found", 404);

    private String message;
    private int code;

    PointsActionError(String message, int code) {
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
