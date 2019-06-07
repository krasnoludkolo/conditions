package io.github.krasnoludkolo;

import io.github.krasnoludkolo.resolver.Action;
import io.github.krasnoludkolo.resolver.Condition;
import io.github.krasnoludkolo.resolver.Resolver;
import io.github.krasnoludkolo.resolver.Success;
import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

final class App {

    public static void main(String[] args) {

        new App().run();


    }

    private void run() {

        UserInspector userInspector = new UserInspector();
        Random random = new Random();
        FantasticService service = new FantasticService(random);

        AdminChecker checker = new AdminChecker(userInspector);
        AlwaysTrueChecker trueChecker = new AlwaysTrueChecker();

        User userFromRequest = new User(1);

        Resolver
                .when(
                        checker.check(userFromRequest),
                        trueChecker.check()
                )
                .perform(
                        getRandomNumber(service)
                );

    }

    @NotNull
    private Action<Integer> getRandomNumber(FantasticService service) {
        return () -> Either.right(service.getRandomNumber());
    }


    class AdminChecker {

        private UserInspector userInspector;

        AdminChecker(UserInspector userInspector) {
            this.userInspector = userInspector;
        }

        Condition check(User user) {
            return () -> userInspector.isAdmin(user);
        }
    }
    class AlwaysTrueChecker {

        Condition check() {
            return () -> Either.right(new Success());
        }
    }
}
