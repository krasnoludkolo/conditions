package io.github.krasnoludkolo;

import io.github.krasnoludkolo.game.GameConfiguration;
import io.github.krasnoludkolo.infrastructure.http.Controller;
import io.github.krasnoludkolo.infrastructure.http.JavalinHandler;
import io.github.krasnoludkolo.points.PointConfiguration;
import io.github.krasnoludkolo.user.UserConfiguration;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.vavr.collection.List;
import io.vavr.jackson.datatype.VavrModule;

final class App {

    void start() {
        PointConfiguration pointConfiguration = PointConfiguration.inMemory();
        UserConfiguration userConfiguration = UserConfiguration.inMemory(pointConfiguration.getPointFacade());
        GameConfiguration gameConfiguration = GameConfiguration.inMemoryWithRandom(pointConfiguration.getPointFacade(), userConfiguration.userCheckers);

        Javalin app = Javalin.create().start(7000);

        List<Controller> controllers = List.of(
                gameConfiguration.gameRestController,
                userConfiguration.userRestController
        );

        List<JavalinHandler> handlers = controllers.flatMap(Controller::handlers);

        addHandlers(app, handlers);

        JavalinJackson.getObjectMapper().registerModule(new VavrModule());
    }


    private Javalin addHandlers(Javalin application, List<JavalinHandler> handlers) {
        return handlers
                .foldLeft(application, this::addHandler);
    }

    private Javalin addHandler(Javalin app, JavalinHandler h) {
        return app
                .addHandler(h.handlerType, h.path, h.handler);
    }

}
