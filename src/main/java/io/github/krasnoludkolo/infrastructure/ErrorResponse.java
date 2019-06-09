package io.github.krasnoludkolo.infrastructure;

import io.github.krasnoludkolo.resolver.SomeError;

public interface ErrorResponse extends SomeError {

    String getMessage();
    int getCode();

}
