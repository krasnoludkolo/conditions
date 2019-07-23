package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.CreateGameRequestDTO;
import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.infrastructure.http.Controller;
import io.github.krasnoludkolo.infrastructure.http.JavalinHandler;
import io.github.krasnoludkolo.infrastructure.http.ResponseResolver;
import io.javalin.Handler;
import io.vavr.collection.List;

public final class GameRestController implements Controller {

    private final Handler createGame;
    private final Handler getGame;
    private final Handler getAllGames;

    GameRestController(GameFacade gameFacade){
        createGame = ctx -> {
            CreateGameRequestDTO dto = ctx.bodyAsClass(CreateGameRequestDTO.class);
            gameFacade.createGame(dto.getMaxNumber(), dto.getUserId())
                    .map(GameDTO::getId)
                    .peek(id -> ctx.redirect("/games/" + id));
        };
        getGame = ctx -> {
            int id = ctx.pathParam("id", Integer.class).get();
            ResponseResolver.resolve(gameFacade.getGameById(id), ctx);
        };

        getAllGames = ctx -> ResponseResolver.resolve(gameFacade.getAllGames(),ctx);
    }

    @Override
    public List<JavalinHandler> handlers() {
        return List.of(
                JavalinHandler.post("/games",createGame),
                JavalinHandler.get("/games",getAllGames),
                JavalinHandler.get("/games/:id",getGame)
        );
    }
}
