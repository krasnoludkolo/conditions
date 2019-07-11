package io.github.krasnoludkolo.game.api;

import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class GameDTO {

    private final UUID uuid;
    private final List<Bet> usersBet;
    private int maxNumber;

}
