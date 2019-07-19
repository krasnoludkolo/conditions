package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.*;
import io.github.krasnoludkolo.infrastructure.ActionError;
import io.github.krasnoludkolo.points.PointConfiguration;
import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.user.UserCheckers;
import io.github.krasnoludkolo.user.UserConfiguration;
import io.github.krasnoludkolo.user.UserFacade;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameFacadeTest {

    private GameFacade gameFacade;
    private UserFacade userFacade;
    private final static int LOOSING_BET = 2;
    private final static int WINNING_BET = 5;

    @Before
    public void init(){
        PointFacade pointFacade = PointConfiguration.inMemory().pointFacade;
        UserConfiguration userConfiguration = UserConfiguration.inMemory(pointFacade);
        UserCheckers userCheckers = userConfiguration.userCheckers;
        userFacade = userConfiguration.userFacade;
        Random random = new Always4Random();
        gameFacade = GameConfiguration.inMemoryWithRandom(pointFacade,userCheckers,random).gameFacade;
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
        int userId = userFacade.createUserWithId(1).getId();
        int gameId = gameFacade.createGame(2).get().getId();

        gameFacade.addBet(new NewBetDTO(gameId, userId, LOOSING_BET));

        BetDTO betDTO = gameFacade.getGameById(gameId).get().getUsersBet().get(0);
        assertEquals(2,betDTO.bet);
        assertEquals(userId,betDTO.userId);
    }

    @Test
    public void shouldEndedGameReturningFinishGameDTO() {
        int id = gameFacade.createGame(10).get().getId();

        gameFacade.endGame(id);

        GameDTO gameDTO = gameFacade.getGameById(id).get();
        assertTrue(gameDTO instanceof FinishedGameDTO);
    }

    @Test
    public void shouldNotBeAbleToMakeImpossibleBet(){
        int maxNumber = 5;
        int impossibleBet = maxNumber + 1;
        int game = gameFacade.createGame(maxNumber).get().getId();
        int user = userFacade.createUserWithId(1).getId();

        ActionError error = gameFacade.addBet(new NewBetDTO(game, user, impossibleBet)).getLeft();

        assertEquals(GameActionError.IMPOSSIBLE_BET,error);
    }

    @Test
    public void shouldWinningNumberBeInResult(){
        int game = gameFacade.createGame(5).get().getId();

        gameFacade.endGame(game);

        FinishedGameDTO gameDTO = (FinishedGameDTO)gameFacade.getGameById(game).get();
        assertEquals(5,gameDTO.winnerNumber);
    }

    @Test
    public void shouldWinWithWinningBet(){
        int game = gameFacade.createGame(5).get().getId();
        int user = userFacade.createUserWithId(1).getId();

        gameFacade.addBet(new NewBetDTO(game,user,WINNING_BET));
        gameFacade.endGame(game);

        FinishedGameDTO gameDTO = (FinishedGameDTO)gameFacade.getGameById(game).get();
        assertTrue(gameDTO.winnersId.contains(user));
    }

}