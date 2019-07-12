package io.github.krasnoludkolo.game.api;

public final class BetDTO {

    public final int gameId;
    public final int userId;
    public final int bet;

    public BetDTO(int gameId, int userId, int bet) {
        this.gameId = gameId;
        this.userId = userId;
        this.bet = bet;
    }


}
