package io.github.krasnoludkolo;

import io.github.krasnoludkolo.infrastructure.http.ResponseResolver;
import io.github.krasnoludkolo.user.UserConfiguration;
import io.github.krasnoludkolo.user.UserFacade;
import io.javalin.Handler;
import io.javalin.Javalin;
import io.vavr.control.Try;

final class App {

    public static void main(String[] args) {

        UserConfiguration userConfiguration = UserConfiguration.inMemory();
        UserFacade userFacade = userConfiguration.getUserFacade();

        Javalin app = Javalin.create().start(7000);

        Handler createUser = ctx -> {
            int id = userFacade.createUser().getId();
            ctx.redirect("/user/" + id);
        };

        Handler getUser = ctx -> Try
                .of(()->Integer.parseInt(ctx.pathParam("id")))
                .map(userFacade::getUserInfo)
                .map(userInfo->ResponseResolver.resolve(userInfo, ctx))
                .get();//TODO handle exception

        app
                .post("/user", createUser)
                .get("/user/:id", getUser);

    }

}
