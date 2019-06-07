package io.github.krasnoludkolo.resolver;

import io.vavr.collection.List;
import io.vavr.control.Either;

public interface Action<T> {

    Either<List<SomeError>,T> perform();

}
