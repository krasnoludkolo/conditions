package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.http.Controller;
import io.github.krasnoludkolo.infrastructure.http.JavalinHandler;
import io.github.krasnoludkolo.infrastructure.http.ResponseResolver;
import io.javalin.Handler;
import io.vavr.collection.List;

public final class UserRestController implements Controller {

    private final Handler getUser;

    UserRestController(UserFacade userFacade) {
        getUser = ctx -> {
            Integer id = ctx.pathParam("id", Integer.class).get();
            ResponseResolver.resolve(userFacade.getUserInfo(id), ctx);
        };
    }

    public List<JavalinHandler> handlers(){
        return List.of(
            JavalinHandler.get("/users/:id",getUser)
        );
    }


}
