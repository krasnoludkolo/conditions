package io.github.krasnoludkolo.auth;

import io.github.krasnoludkolo.auth.api.ApiToken;
import io.javalin.Context;
import io.javalin.Handler;
import io.javalin.core.util.Header;
import io.javalin.security.AccessManager;
import io.javalin.security.Role;
import io.vavr.control.Option;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class TokenAccessManager implements AccessManager {

    public static final String ID_HEADER = "ID";
    public static final String EMPTY_ID = "";
    private final AuthFacade authFacade;

    TokenAccessManager(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @Override
    public void manage(@NotNull Handler handler, @NotNull Context ctx, @NotNull Set<Role> permittedRoles) throws Exception {
        Context context = Option.of(ctx.header(Header.AUTHORIZATION))
                .map(token -> setHeaderWithId(token, ctx))
                .getOrElseTry(() -> setEmptyHeader(ctx));
        handler.handle(context);
    }

    private Context setHeaderWithId(String token, Context context) {
        return authFacade.getIdFromToken(new ApiToken(token))
                .map(id -> context.header(ID_HEADER, id.toString()))
                .getOrElse(context);
    }

    private Context setEmptyHeader(Context ctx) {
        return ctx.header(ID_HEADER, EMPTY_ID);
    }

}
