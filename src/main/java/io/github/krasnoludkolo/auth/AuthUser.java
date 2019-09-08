package io.github.krasnoludkolo.auth;

import io.github.krasnoludkolo.infrastructure.Identifiable;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

final class AuthUser implements Identifiable<Integer>, Serializable {

    private final int id;
    final String password;

    private static AtomicInteger currentId = new AtomicInteger(0); //for simplicity

    static AuthUser create(String password){
        int id = currentId.getAndIncrement();
        return new AuthUser(id,password);
    }

    private AuthUser(int id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
