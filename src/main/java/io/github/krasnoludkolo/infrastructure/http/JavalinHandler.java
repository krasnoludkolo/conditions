package io.github.krasnoludkolo.infrastructure.http;

import io.javalin.Handler;
import io.javalin.core.HandlerType;

public class JavalinHandler {

    public final HandlerType handlerType;
    public final String path;
    public final Handler handler;

    public static JavalinHandler get(String path, Handler handler){
        return new JavalinHandler(HandlerType.GET,path,handler);
    }

    public static JavalinHandler post(String path, Handler handler){
        return new JavalinHandler(HandlerType.POST,path,handler);
    }

    private JavalinHandler(HandlerType handlerType, String path, Handler handler) {
        this.handlerType = handlerType;
        this.path = path;
        this.handler = handler;
    }

}
