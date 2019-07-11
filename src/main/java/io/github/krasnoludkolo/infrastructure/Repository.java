package io.github.krasnoludkolo.infrastructure;

import io.github.krasnoludkolo.resolver.Success;
import io.vavr.collection.List;
import io.vavr.control.Option;

public interface Repository<T extends Identifiable<Integer>> {
    T save(T t);

    Option<T> findOne(int id);

    List<T> findAll();

    Success delete(int id);

    Success update(T t);
}
