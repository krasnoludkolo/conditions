package io.github.krasnoludkolo;

import io.github.krasnoludkolo.user.UserConfiguration;
import io.github.krasnoludkolo.user.UserFacade;

final class App {

    public static void main(String[] args) {
        //TODO run javalin server

        UserConfiguration userConfiguration = UserConfiguration.inMemory();
        UserFacade userFacade = userConfiguration.getUserFacade();


    }

}
