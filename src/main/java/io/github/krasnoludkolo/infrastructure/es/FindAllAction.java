package io.github.krasnoludkolo.infrastructure.es;

import io.github.krasnoludkolo.infrastructure.Identifiable;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.vavr.collection.List;
import pl.setblack.airomem.core.Query;

import java.io.Serializable;

final class FindAllAction<T extends Identifiable<Integer>> implements Query<Repository<T>, List<T>>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<T> evaluate(Repository<T> system) {
        return system.findAll();
    }
}
