package io.github.krasnoludkolo.user;

final class User {

    final int id;
    final boolean isAdmin;

    static int currentId = 0; //for simplicity

    static User createNormal(){
        int id = currentId;
        currentId++;
        return new User(id,false);
    }

    static User createAdmin(){
        int id = currentId;
        currentId++;
        return new User(id,true);
    }

    private User(int id, boolean isAdmin) {
        this.id = id;
        this.isAdmin = isAdmin;
    }

    User promoteToAdmin(){
        return new User(id,true);
    }
}
