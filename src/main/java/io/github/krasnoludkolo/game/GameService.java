package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.game.api.NewBetDTO;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.resolver.Action;
import io.vavr.collection.List;
import io.vavr.control.Option;

final class GameService {

    private final Repository<Game> repository;
    private final PointFacade pointFacade;

    GameService(Repository<Game> repository, PointFacade pointFacade) {
        this.repository = repository;
        this.pointFacade = pointFacade;
    }

    Action<GameDTO> addBet(NewBetDTO bet){
        return () -> repository
                .findOne(bet.gameId)
                .map(g->g.addBet(bet))
                .map(repository::save)
                .map(Game::toDTO)
                .get();
    }

    Action<GameDTO> endGame(int id) {
        return ()-> repository
                .findOne(id)
                .map(g->g.endGame(pointFacade))
                .map(repository::save)
                .map(Game::toDTO)
                .get();
    }

    Action<GameDTO> createGame(int maxNumber) {
        return ()->null;
    }

    List<GameDTO> getAllGames() {
        return repository
                .findAll()
                .map(Game::toDTO);
    }

    Option<GameDTO> getGameById(int id) {
        return repository
                .findOne(id)
                .map(Game::toDTO);
    }
}
