package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.BetDTO;
import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.game.api.NewBetDTO;
import io.github.krasnoludkolo.points.PointConfiguration;
import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.user.UserCheckers;
import io.github.krasnoludkolo.user.UserConfiguration;
import io.github.krasnoludkolo.user.UserFacade;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class GameFacadeTest {


    private GameFacade gameFacade;
    private UserFacade userFacade;
    private final static int LOOSING_BET = 2;
    private final static int WINNING_BET = 4;

    @Before
    public void init(){
        PointFacade pointFacade = PointConfiguration.inMemory().getPointFacade();
        UserConfiguration userConfiguration = UserConfiguration.inMemory(pointFacade);
        UserCheckers userCheckers = userConfiguration.getUserCheckers();
        userFacade = userConfiguration.getUserFacade();
        Random random = new Always4Random();
        gameFacade = GameConfiguration.inMemoryWithRandom(pointFacade,userCheckers,random).getGameFacade();
    }


    @Test
    public void shouldCreateAndGetGame() {
        Integer id = gameFacade
                .createGame(10)
                .get()
                .getId();
        Option<GameDTO> gameById = gameFacade.getGameById(id);
        assertTrue(gameById.isDefined());
    }

    @Test
    public void shouldNotGetNotExistingGame() {
        Option<GameDTO> gameById = gameFacade.getGameById(1);
        assertTrue(gameById.isEmpty());
    }

    @Test
    public void shouldGetAllCreatedGames() {
        gameFacade.createGame(1);
        gameFacade.createGame(1);
        gameFacade.createGame(1);

        int size = gameFacade.getAllGames().size();
        assertEquals(3,size);
    }

    @Test
    public void shouldNewGameHasEmptyBetList() {
        int id = gameFacade.createGame(1).get().getId();

        int size = gameFacade.getGameById(id).get().getUsersBet().size();

        assertEquals(0,size);
    }

    @Test
    public void shouldAddBet() {
        int userId = userFacade.createUser().getId();
        int gameId = gameFacade.createGame(2).get().getId();

        gameFacade.addBet(new NewBetDTO(gameId, userId, LOOSING_BET));

        BetDTO betDTO = gameFacade.getGameById(gameId).get().getUsersBet().get(0);
        assertEquals(2,betDTO.bet);
        assertEquals(userId,betDTO.userId);
    }

    @Test
    public void endGame() {
        fail("Not implemented");
    }
}