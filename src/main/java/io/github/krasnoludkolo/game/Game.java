package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.BetDTO;
import io.github.krasnoludkolo.game.api.FinishedGameDTO;
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

    boolean isBetPossible(int bet) {
        return bet >= 0 && bet <= maxNumber;
    }

    GameDTO toDTO() {
        List<BetDTO> b = getBetsAsBetDTOList();
        return new GameDTO(id, b, maxNumber);
    }

    private List<BetDTO> getBetsAsBetDTOList() {
        return bets.values().map(Bet::toBetDTO).toList();
    }

    Game addBet(NewBetDTO newBet) {
        Bet bet = Bet.from(newBet);
        Map<Integer, Bet> newBetMap = bets.put(newBet.userId, bet);
        return new Game(id, maxNumber, newBetMap, random);
    }

    FinishedGame endGame(PointFacade pointFacade) {
        return resolveThisGame();
    }

    private FinishedGame resolveThisGame() {
        int winningNumber = random.nextInt(maxNumber)+1;
        List<Integer> winners = getWinners(winningNumber);
        return new FinishedGame(winners, winningNumber);
    }

    private List<Integer> getWinners(int winningNumber) {
        return bets
                .filterValues(bet -> bet.correct(winningNumber))
                .keySet()
                .toList();
    }

    @Override
    public Integer getId() {
        return id;
    }

    class FinishedGame extends Game {

        private final List<Integer> winners;
        private final int winnerNumber;


        private FinishedGame(List<Integer> winners, int winnerNumber) {
            super(id, maxNumber, bets, random);
            this.winners = winners;
            this.winnerNumber = winnerNumber;
        }

        @Override
        GameDTO toDTO() {
            List<BetDTO> b = getBetsAsBetDTOList();
            return new FinishedGameDTO(id, b,maxNumber,winners,winnerNumber);
        }

        @Override
        boolean isBetPossible(int bet) {
            return false;
        }

        @Override
        Game addBet(NewBetDTO bet) {
            return this;
        }

        @Override
        FinishedGame endGame(PointFacade pointFacade) {
            return this;
        }
    }
}
