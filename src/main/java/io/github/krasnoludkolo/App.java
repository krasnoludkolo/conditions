package io.github.krasnoludkolo;

import io.github.krasnoludkolo.game.GameConfiguration;
import io.github.krasnoludkolo.game.GameFacade;
import io.github.krasnoludkolo.game.api.GameDTO;
import io.github.krasnoludkolo.infrastructure.http.ResponseResolver;
import io.github.krasnoludkolo.points.PointConfiguration;
import io.github.krasnoludkolo.user.UserConfiguration;
import io.github.krasnoludkolo.user.UserFacade;
import io.javalin.Handler;
import io.javalin.Javalin;

final class App {

    void start() {

        PointConfiguration pointConfiguration = PointConfiguration.inMemory();

        UserConfiguration userConfiguration = UserConfiguration.inMemory(pointConfiguration.getPointFacade());
        UserFacade userFacade = userConfiguration.getUserFacade();

        GameConfiguration gameConfiguration = GameConfiguration.inMemoryWithRandom(pointConfiguration.getPointFacade(), userConfiguration.getUserCheckers());
        GameFacade gameFacade = gameConfiguration.getGameFacade();

        Javalin app = Javalin.create().start(7000);

        Handler createUser = ctx -> {
            int id = userFacade.createUser().getId();
            ctx.redirect("/user/" + id);
        };

        Handler createGame = ctx -> {
            Integer maxNumber = ctx.pathParam("max", Integer.class).get();
            gameFacade.createGame(maxNumber)
                    .map(GameDTO::getId)
                    .peek(id -> ctx.redirect("/game/" + id));
        };

        Handler getUser = ctx -> {
            Integer id = ctx.queryParam("id", Integer.class).get();
            ResponseResolver.resolve(userFacade.getUserInfo(id), ctx);
        };

        app
                .post("/user", createUser)
                .post("/game", createGame)
                .get("/user/:id", getUser);

    }


}
