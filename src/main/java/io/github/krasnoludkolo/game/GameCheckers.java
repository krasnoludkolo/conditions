package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.GameActionError;
import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.resolver.Condition;
import io.github.krasnoludkolo.resolver.Success;

final class GameCheckers {

    private final Repository<Game> repository;


    GameCheckers(Repository<Game> repository) {
        this.repository = repository;
    }

    public Condition<ActionError> gameExists(int gameId) {
        return () -> repository
                .findOne(gameId)
                .toEither((ActionError)GameActionError.GAME_NOT_FOUND)
                .map(Success::new);
    }
}
