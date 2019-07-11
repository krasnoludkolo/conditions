package io.github.krasnoludkolo.game.api;

import io.vavr.collection.List;

import java.util.UUID;

public class FinishedGameDTO extends GameDTO{

    public final List<Integer> winnersId;
    public final int winnerNumber;

    public FinishedGameDTO(UUID uuid, List<Bet> usersBet, int maxNumber, List<Integer> winnersId, int winnerNumber) {
        super(uuid, usersBet, maxNumber);
        this.winnersId = winnersId;
        this.winnerNumber = winnerNumber;
    }

}
