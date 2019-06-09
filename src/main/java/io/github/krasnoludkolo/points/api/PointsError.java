package io.github.krasnoludkolo.points.api;

import io.github.krasnoludkolo.infrastructure.ErrorResponse;

public enum PointsError implements ErrorResponse {
    USER_NOT_FOUND("user not found", 404);

    private String message;
    private int code;

    PointsError(String message, int code) {
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
