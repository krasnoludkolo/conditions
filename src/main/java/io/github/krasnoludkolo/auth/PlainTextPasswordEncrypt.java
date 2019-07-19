package io.github.krasnoludkolo.auth;

final class PlainTextPasswordEncrypt implements PasswordEncrypt {

    @Override
    public String encryptPassword(String password) {
        return password;
    }

    @Override
    public boolean checkPassword(String candidate, String password) {
        return candidate.equals(password);
    }
}
