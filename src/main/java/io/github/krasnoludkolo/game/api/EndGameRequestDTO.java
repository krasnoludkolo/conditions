package io.github.krasnoludkolo.game.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class EndGameRequestDTO {

    private int gameId;
    private int userId;

}
