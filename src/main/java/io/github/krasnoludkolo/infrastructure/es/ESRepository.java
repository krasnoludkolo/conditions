package io.github.krasnoludkolo.infrastructure.es;

import io.github.krasnoludkolo.infrastructure.Identifiable;
import io.github.krasnoludkolo.infrastructure.InMemoryRepository;
import io.github.krasnoludkolo.infrastructure.Repository;
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
        return controller.executeAndQuery(new SaveAction<>(t));
    }

    @Override
    public Option<T> findOne(int id) {
        return controller.query(new FindOneAction<>(id));
    }

    @Override
    public List<T> findAll() {
        return controller.query(new FindAllAction<>());
    }

    @Override
    public Success delete(int id) {
        return controller.executeAndQuery(new DeleteAction<>(id));
    }

    @Override
    public Success update(final T t) { //TODO return type Success or T?
        return controller.executeAndQuery(new UpdateAction<>(t));
    }
}
