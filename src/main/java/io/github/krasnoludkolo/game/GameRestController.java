package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.game.api.CreateGameRequestDTO;
import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.infrastructure.http.Controller;
import io.github.krasnoludkolo.infrastructure.http.JavalinHandler;
import io.github.krasnoludkolo.infrastructure.http.ResponseResolver;
import io.javalin.Context;
import io.vavr.collection.List;

public final class GameRestController implements Controller {

    private final GameFacade gameFacade;

    GameRestController(GameFacade gameFacade) {
        this.gameFacade = gameFacade;
    }

    @Override
    public List<JavalinHandler> handlers() {
        return List.of(
                JavalinHandler.post("/games", this::createGame),
                JavalinHandler.get("/games", this::getAllGames),
                JavalinHandler.get("/games/:id", this::getGame)
        );
    }

    private void createGame(Context ctx) {
            CreateGameRequestDTO dto = ctx.bodyAsClass(CreateGameRequestDTO.class);
            gameFacade.createGame(dto.getMaxNumber(), dto.getUserId())
                    .map(GameDTO::getId)
                    .peek(id -> ctx.redirect("/games/" + id));
    }

    private void getAllGames(Context ctx){
        ResponseResolver
                .resolve(gameFacade.getAllGames(), ctx);
    }
    private void getGame(Context ctx){
        int id = ctx.pathParam("id", Integer.class).get();
        ResponseResolver
                .resolve(gameFacade.getGameById(id), ctx);
    }
    
}
