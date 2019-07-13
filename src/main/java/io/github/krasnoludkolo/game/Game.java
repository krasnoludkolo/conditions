package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.BetDTO;
import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.game.api.NewBetDTO;
import io.github.krasnoludkolo.infrastructure.Identifiable;
import io.github.krasnoludkolo.points.PointFacade;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class Game implements Identifiable<Integer> {

    private final int id;
    private final int maxNumber;
    private final Map<Integer, Bet> bets;
    private final Random random;

    private static AtomicInteger currentId = new AtomicInteger(0); //for simplicity

    static Game create(int maxNumber, Random random) {
        int id = currentId.getAndIncrement();
        return new Game(id, maxNumber, HashMap.empty(), random);
    }

    private Game(int id, int maxNumber, Map<Integer, Bet> bets, Random random) {
        this.id = id;
        this.maxNumber = maxNumber;
        this.bets = bets;
        this.random = random;
    }

    GameDTO toDTO() {
        List<BetDTO> b = getBetsAsBetDTOList();
        return new GameDTO(id, b, maxNumber);
    }

    private List<BetDTO> getBetsAsBetDTOList() {
        return bets.values().map(Bet::toBetDTO).toList();
    }

    Game addBet(NewBetDTO bet) {
        Bet newBet = new Bet(bet.userId, bet.bet);
        Map<Integer, Bet> newBetMap = bets.put(bet.userId, newBet);
        return new Game(id, maxNumber, newBetMap, random);
    }

    Game endGame(PointFacade pointFacade) {
        return null;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
