package io.github.krasnoludkolo;

import io.github.krasnoludkolo.points.PointFacade;
import io.github.krasnoludkolo.user.UserFacade;

final class App {

    public static void main(String[] args) {

        UserFacade userFacade = new UserFacade();
        PointFacade pointFacade = new PointFacade(userFacade.getUserCheckers());
        //TODO run javalin server
    }

}
