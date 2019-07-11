package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.Bet;
import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.resolver.Action;

final class GameService {

    private final Repository<Game> repository;

    GameService(Repository<Game> repository) {
        this.repository = repository;
    }

    Action<Game> addBet(Bet bet){
        return () -> repository
                .findOne(bet.gameId)
                .map(g->g.addBet(bet))
                .map(repository::save)
                .get();
    }

    Action<Game> endGame(int id, PointFacade pointFacade) {
        return ()-> repository
                .findOne(id)
                .map(g->g.endGame(pointFacade))
                .map(repository::save)
                .get();
    }

    GameDTO createGame() {
        return null;
    }
}
