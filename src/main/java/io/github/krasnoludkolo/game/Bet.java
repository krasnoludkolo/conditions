package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.BetDTO;
import io.github.krasnoludkolo.game.api.NewBetDTO;

final class Bet {

    private int userId;
    private int bet;

    static Bet from(NewBetDTO dto){
        return new Bet(dto.userId,dto.bet);
    }

    private Bet(int userId, int bet) {
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
