package io.github.krasnoludkolo.user;

import io.github.krasnoludkolo.infrastructure.Identifiable;
import io.github.krasnoludkolo.user.api.UserDTO;

import java.io.Serializable;

final class User implements Identifiable<Integer>, Serializable {

    private static final long serialVersionUID = 1L;

    private final int id;
    final boolean isAdmin;

    static User createNormal(int id){
        return new User(id,false);
    }

    static User createAdmin(int id){
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
