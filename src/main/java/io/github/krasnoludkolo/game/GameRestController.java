package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.infrastructure.http.Controller;
import io.github.krasnoludkolo.infrastructure.http.JavalinHandler;
import io.github.krasnoludkolo.infrastructure.http.ResponseResolver;
import io.javalin.Handler;
import io.vavr.collection.List;

public final class GameRestController implements Controller {

    private final Handler createGame;
    private final Handler getGame;

    GameRestController(GameFacade gameFacade){
        createGame = ctx -> {
            int maxNumber = ctx.queryParam("max", Integer.class).get();
            gameFacade.createGame(maxNumber)
                    .map(GameDTO::getId)
                    .peek(id -> ctx.redirect("/game/" + id));
        };
        getGame = ctx -> {
            int id = ctx.pathParam("id", Integer.class).get();
            ResponseResolver.resolve(gameFacade.getGameById(id), ctx);
        };

    }

    @Override
    public List<JavalinHandler> handlers() {
        return List.of(
                JavalinHandler.post("/game",createGame),
                JavalinHandler.get("/game/:id",getGame)
        );
    }
}
