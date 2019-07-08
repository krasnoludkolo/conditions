package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.Identifiable;
import io.github.krasnoludkolo.user.api.UserDTO;

import java.util.concurrent.atomic.AtomicInteger;

final class User implements Identifiable<Integer> {

    private final int id;
    final boolean isAdmin;

    private static AtomicInteger currentId = new AtomicInteger(0); //for simplicity

    static User createNormal(){
        int id = currentId.getAndIncrement();
        return new User(id,false);
    }

    static User createAdmin(){
        int id = currentId.getAndIncrement();
        return new User(id,true);
    }

    private User(int id, boolean isAdmin) {
        this.id = id;
        this.isAdmin = isAdmin;
    }

    User promoteToAdmin(){
        return new User(id,true);
    }

    UserDTO toDTO(){
        return new UserDTO(id,isAdmin);
    }

    @Override
    public Integer getId() {
        return id;
    }
}
