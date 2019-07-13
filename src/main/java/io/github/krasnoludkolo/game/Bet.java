package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.BetDTO;

final class Bet {

    private int userId;
    private int bet;

    Bet(int userId, int bet) {
        this.userId = userId;
        this.bet = bet;
    }

    boolean correct(int n){
        return n == bet;
    }

    BetDTO toBetDTO(){
        return new BetDTO(userId,bet);
    }

}
