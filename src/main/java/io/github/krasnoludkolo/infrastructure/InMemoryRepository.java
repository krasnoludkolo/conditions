package io.github.krasnoludkolo.infrastructure;

import io.github.krasnoludkolo.resolver.Success;
import io.vavr.collection.List;
import io.vavr.control.Option;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository<T extends Identifiable<Integer>> implements Repository<T> {

    private ConcurrentHashMap<Integer, T> map = new ConcurrentHashMap<>();

    @Override
    public T save(T t) {
        map.put(t.getId(), t);
        return t;
    }

    @Override
    public Option<T> findOne(int id) {
        return Option.of(map.get(id));
    }

    @Override
    public List<T> findAll() {
        return List.ofAll(map.values());
    }

    @Override
    public Success delete(int id) {
        map.remove(id);
        return new Success();
    }

    @Override
    public Success update(T t) {
        map.put(t.getId(), t);
        return new Success();
    }

}
