package io.github.krasnoludkolo;

import io.github.krasnoludkolo.auth.AuthConfiguration;
import io.github.krasnoludkolo.game.GameConfiguration;
import io.github.krasnoludkolo.infrastructure.http.Controller;
import io.github.krasnoludkolo.infrastructure.http.JavalinHandler;
import io.github.krasnoludkolo.infrastructure.http.ServerResponse;
import io.github.krasnoludkolo.points.PointConfiguration;
import io.github.krasnoludkolo.user.UserConfiguration;
import io.javalin.Context;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.vavr.collection.List;
import io.vavr.jackson.datatype.VavrModule;

final class App {

    private static final String API_PREFIX = "/api";

    void start() {
        PointConfiguration pointConfiguration = PointConfiguration.withEs();
        UserConfiguration userConfiguration = UserConfiguration.withEs(pointConfiguration.pointFacade);
        GameConfiguration gameConfiguration = GameConfiguration.withEs(pointConfiguration.pointFacade, userConfiguration.userCheckers);
        AuthConfiguration authConfiguration = AuthConfiguration.withEs(userConfiguration.userFacade);

        Javalin app = Javalin
                .create()
                .accessManager(authConfiguration.tokenAccessManager)
                .error(404, this::notFoundErrorHandler)
                .start(7000);

        List<Controller> controllers = List.of(
                gameConfiguration.gameRestController,
                userConfiguration.userRestController,
                authConfiguration.loginRestController
        );

        List<JavalinHandler> handlers = controllers.flatMap(Controller::handlers);

        addHandlers(app, handlers);

        JavalinJackson.getObjectMapper().registerModule(new VavrModule());
    }

    private void notFoundErrorHandler(Context context) {
        context.json(new ServerResponse("Not found"));
    }


    private Javalin addHandlers(Javalin application, List<JavalinHandler> handlers) {
        return handlers
                .foldLeft(application, this::addHandler);
    }

    private Javalin addHandler(Javalin app, JavalinHandler h) {
        return app
                .addHandler(h.handlerType, API_PREFIX + h.path, h.handler);
    }

}
