package io.github.krasnoludkolo.auth;

import io.github.krasnoludkolo.auth.api.LoginRequestDTO;
import io.github.krasnoludkolo.auth.api.RegisterRequestDTO;
import io.github.krasnoludkolo.infrastructure.http.Controller;
import io.github.krasnoludkolo.infrastructure.http.JavalinHandler;
import io.github.krasnoludkolo.infrastructure.http.ResponseResolver;
import io.javalin.Context;
import io.vavr.collection.List;

public class LoginRestController implements Controller {

    private final AuthFacade authFacade;

    LoginRestController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @Override
    public List<JavalinHandler> handlers() {
        return List.of(
                JavalinHandler.post("/login", this::login),
                JavalinHandler.post("/register", this::register)
        );
    }

    private void login(Context ctx) {
        LoginRequestDTO loginDTO = ctx.bodyAsClass(LoginRequestDTO.class);
        int id = loginDTO.id;
        String password = loginDTO.password;
        ResponseResolver.resolve(authFacade.login(id, password), ctx);
    }

    private void register(Context ctx) {
        RegisterRequestDTO registerDTO = ctx.bodyAsClass(RegisterRequestDTO.class);
        String password = registerDTO.password;
        ResponseResolver.resolve(authFacade.register(password), ctx);
    }

}
