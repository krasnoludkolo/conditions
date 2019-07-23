package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.game.api.NewBetDTO;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.resolver.Action;
import io.vavr.collection.List;
import io.vavr.control.Option;

import java.util.Random;

final class GameService {

    private final Repository<Game> repository;
    private final PointFacade pointFacade;
    private final Random random;

    GameService(Repository<Game> repository, PointFacade pointFacade, Random random) {
        this.repository = repository;
        this.pointFacade = pointFacade;
        this.random = random;
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

    Action<GameDTO> createGame(int maxNumber, int gameCreatorId) {
        return ()->Option.of(Game.create(maxNumber,random,gameCreatorId))
                .map(repository::save)
                .map(Game::toDTO)
                .get();
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
