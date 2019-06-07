package io.github.krasnoludkolo.resolver;

import io.vavr.collection.List;
import io.vavr.control.Either;

public interface Condition {

    Either<List<SomeError>,Success> test();

}
