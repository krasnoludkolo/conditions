package io.github.krasnoludkolo.infrastructure.http;

import io.vavr.collection.List;

public interface Controller {

    List<JavalinHandler> handlers();

}
