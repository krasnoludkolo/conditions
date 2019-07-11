package io.github.krasnoludkolo;

import io.github.krasnoludkolo.infrastructure.http.ResponseResolver;
import io.github.krasnoludkolo.user.UserConfiguration;
import io.github.krasnoludkolo.user.UserFacade;
import io.javalin.Handler;
import io.javalin.Javalin;

final class App {

    void start() {

        UserConfiguration userConfiguration = UserConfiguration.inMemory();
        UserFacade userFacade = userConfiguration.getUserFacade();

        Javalin app = Javalin.create().start(7000);

        Handler createUser = ctx -> {
            int id = userFacade.createUser().getId();
            ctx.redirect("/user/" + id);
        };

        Handler getUser = ctx -> {
            Integer id = ctx.queryParam("id", Integer.class).get();
            ResponseResolver.resolve(userFacade.getUserInfo(id), ctx);
        };

        app
                .post("/user", createUser)
                .get("/user/:id", getUser);

    }


}
