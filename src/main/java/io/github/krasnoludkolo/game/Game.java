package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.Bet;
import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.infrastructure.Identifiable;
import io.github.krasnoludkolo.points.PointFacade;

final class Game  implements Identifiable<Integer> {

    private int id;


    @Override
    public Integer getId() {
        return id;
    }

    GameDTO toDTO() {
        return null;
    }

     Game addBet(Bet bet) {
        return this;
    }

    Game endGame(PointFacade pointFacade) {
        return null;
    }
}
