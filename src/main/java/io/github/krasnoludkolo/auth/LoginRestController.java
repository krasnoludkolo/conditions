package io.github.krasnoludkolo.auth;

import io.github.krasnoludkolo.infrastructure.http.Controller;
import io.github.krasnoludkolo.infrastructure.http.JavalinHandler;
import io.vavr.collection.List;

public class LoginRestController implements Controller {

    private final AuthFacade authFacade;

    LoginRestController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @Override
    public List<JavalinHandler> handlers() {
        return List.of(

        );
    }
}
