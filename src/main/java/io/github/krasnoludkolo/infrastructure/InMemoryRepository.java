package io.github.krasnoludkolo.infrastructure;
import io.vavr.collection.List;
import io.vavr.control.Option;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository<T> {

    private ConcurrentHashMap<Integer, T> map = new ConcurrentHashMap<>();

    public void save(Integer uuid, T t) {
        map.put(uuid, t);
    }

    public Option<T> findOne(int id) {
        return Option.of(map.get(id));
    }

    public List<T> findAll() {
        return List.ofAll(map.values());
    }

    public void delete(int id) {
        map.remove(id);
    }

    public void update(Integer id, T t) {
        map.put(id, t);
    }

}
