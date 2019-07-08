package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.InMemoryRepository;

public final class UserConfiguration {

    private UserFacade userFacade;
    private UserCheckers userCheckers;

    public static UserConfiguration inMemory(){
        return new UserConfiguration();
    }

    private UserConfiguration(){
        InMemoryRepository<User> repository = new InMemoryRepository<>();
        userCheckers = new UserCheckers(repository);
        userFacade = new UserFacade(userCheckers,repository);
    }

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public UserCheckers getUserCheckers() {
        return userCheckers;
    }
}
