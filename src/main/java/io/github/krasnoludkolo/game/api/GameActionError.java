package io.github.krasnoludkolo.game.api;

import io.github.krasnoludkolo.infrastructure.ActionError;

public enum GameActionError implements ActionError {

    GAME_NOT_FOUND("Game not found",404);

    private String message;
    private int code;

    GameActionError(String message, int code) {
        this.message = message;
        this.code = code;
    }


    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public int getCode() {
        return 0;
    }
}
