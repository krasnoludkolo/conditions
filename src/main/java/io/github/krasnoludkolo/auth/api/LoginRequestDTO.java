package io.github.krasnoludkolo.auth.api;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class LoginRequestDTO {

    public int id;
    public String password;

}
