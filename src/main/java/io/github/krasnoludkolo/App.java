package io.github.krasnoludkolo;

import io.github.krasnoludkolo.user.UserConfiguration;
import io.github.krasnoludkolo.user.UserFacade;
import io.javalin.Handler;
import io.javalin.Javalin;

final class App {

    public static void main(String[] args) {
        //TODO run javalin server

        UserConfiguration userConfiguration = UserConfiguration.inMemory();
        UserFacade userFacade = userConfiguration.getUserFacade();

        Javalin app = Javalin.create().start(7000);

        Handler createUser = ctx -> {
            int id = userFacade.createUser().getId();
            ctx.redirect("/user/" + id);
        };

        Handler getUser = ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(userFacade.getUserInfo(id));
        };

        app.post("/user", createUser);
        app.get("/user/:id", getUser);

    }

}
