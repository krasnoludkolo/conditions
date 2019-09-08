package io.github.krasnoludkolo.infrastructure;

import io.github.krasnoludkolo.resolver.Success;
import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.setblack.airomem.core.PersistenceController;
import pl.setblack.airomem.core.builders.PrevaylerBuilder;

public final class ESRepository<T extends Identifiable<Integer>> implements Repository<T> {

    private final transient PersistenceController<Repository<T>> controller;

    public ESRepository(Class<T> type) {
        controller = PrevaylerBuilder
                .<Repository<T>>newBuilder()
                .withinUserFolder("testy/es/" + type.getSimpleName() + "Repository")
                .useSupplier(InMemoryRepository::new)
                .build();
    }

    @Override
    public T save(final T t) {
        return controller.executeAndQuery(repository -> repository.save(t));
    }

    @Override
    public Option<T> findOne(int id) {
        return controller.query(repository -> repository.findOne(id));
    }

    @Override
    public List<T> findAll() {
        return controller.query(Repository::findAll);
    }

    @Override
    public Success delete(int id) {
        return controller.executeAndQuery(repository -> repository.delete(id));
    }

    @Override
    public Success update(final T t) { //TODO return type Success or T?
        return controller.executeAndQuery(repository -> repository.update(t));
    }
}
