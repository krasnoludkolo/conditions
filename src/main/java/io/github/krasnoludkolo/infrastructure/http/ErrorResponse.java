package io.github.krasnoludkolo.infrastructure.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
class ErrorResponse {

    private final String message;

}
