package io.github.krasnoludkolo.auth;

interface PasswordEncrypt {

    String encryptPassword(String password);
    boolean checkPassword(String candidate, String password);

}
