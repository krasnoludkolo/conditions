package io.github.krasnoludkolo.game.api;

import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameDTO {

    private final Integer id;
    private final List<BetDTO> usersBet;
    private int maxNumber;

}
