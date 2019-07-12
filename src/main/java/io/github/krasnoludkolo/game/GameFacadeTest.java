package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.points.PointConfiguration;
import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.user.UserCheckers;
import io.github.krasnoludkolo.user.UserConfiguration;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameFacadeTest {


    private GameFacade gameFacade;

    @Before
    public void init(){
        PointFacade pointFacade = PointConfiguration.inMemory().getPointFacade();
        UserCheckers userCheckers = UserConfiguration.inMemory(pointFacade).getUserCheckers();
        gameFacade = GameConfiguration.inMemory(pointFacade,userCheckers).getGameFacade();
    }


    @Test
    public void shouldCreateAndGetGame() {
        Integer id = gameFacade.createGame(10).get().getId();
        Option<GameDTO> gameById = gameFacade.getGameById(id);
        assertTrue(gameById.isDefined());
    }

    @Test
    public void getAllGames() {
    }

    @Test
    public void addBet() {
    }

    @Test
    public void endGame() {
    }
}