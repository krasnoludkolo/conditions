package io.github.krasnoludkolo.user.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class UserDTO {

    private int id;
    private boolean isAdmin;

}
