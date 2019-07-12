package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.BetDTO;
import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.infrastructure.Identifiable;
import io.github.krasnoludkolo.points.PointFacade;

import java.util.concurrent.atomic.AtomicInteger;

class Game implements Identifiable<Integer> {

    private final int id;
    private final int maxNumber;

    private static AtomicInteger currentId = new AtomicInteger(0); //for simplicity

    static Game create(int maxNumber){
        int id = currentId.getAndIncrement();
        return new Game(id, maxNumber);
    }

    private Game(int id, int maxNumber) {
        this.id = id;
        this.maxNumber = maxNumber;
    }

    GameDTO toDTO(){
        return null;
    }

    Game addBet(BetDTO bet){
        return null;
    }

    Game endGame(PointFacade pointFacade){
        return null;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
