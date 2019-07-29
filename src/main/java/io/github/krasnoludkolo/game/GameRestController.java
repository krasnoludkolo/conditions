package io.github.krasnoludkolo.game;

import io.github.krasnoludkolo.auth.TokenAccessManager;
import io.github.krasnoludkolo.game.api.CreateGameRequestDTO;
import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.infrastructure.http.Controller;
import io.github.krasnoludkolo.infrastructure.http.JavalinHandler;
import io.github.krasnoludkolo.infrastructure.http.ResponseResolver;
import io.github.krasnoludkolo.infrastructure.http.ServerResponse;
import io.javalin.Context;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;

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
        getUserIdFromHeader(ctx)
                .peek(userId -> addGame(dto, userId, ctx))
                .onEmpty(() -> returnError(ctx));
    }

    private void addGame(CreateGameRequestDTO dto, Integer userId, Context ctx) {
        gameFacade.createGame(dto.getMaxNumber(), userId)
                .map(GameDTO::getId)
                .peek(id -> ctx.redirect("/games/" + id));
    }

    private Option<Integer> getUserIdFromHeader(Context ctx) {
        return Option.of(ctx.header(TokenAccessManager.ID_HEADER))
                .flatMap(header -> Try.of(() -> Integer.parseInt(header)).toOption());
    }

    private void returnError(Context ctx) {
        ctx.json(new ServerResponse("Wrong token"))
                .status(401);
    }

    private void getAllGames(Context ctx) {
        ResponseResolver
                .resolve(gameFacade.getAllGames(), ctx);
    }

    private void getGame(Context ctx) {
        int id = ctx.pathParam("id", Integer.class).get();
        ResponseResolver
                .resolve(gameFacade.getGameById(id), ctx);
    }

}
