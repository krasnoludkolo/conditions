package io.github.krasnoludkolo.game.api;

import io.vavr.collection.List;

public class FinishedGameDTO extends GameDTO{

    public final List<Integer> winnersId;
    public final int winnerNumber;

    public FinishedGameDTO(int id, List<BetDTO> usersBet, int maxNumber, List<Integer> winnersId, int winnerNumber) {
        super(id, usersBet, maxNumber);
        this.winnersId = winnersId;
        this.winnerNumber = winnerNumber;
    }

}
