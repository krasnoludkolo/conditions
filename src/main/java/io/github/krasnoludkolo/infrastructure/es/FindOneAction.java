package io.github.krasnoludkolo.infrastructure.es;

import io.github.krasnoludkolo.infrastructure.Identifiable;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.vavr.control.Option;
import pl.setblack.airomem.core.Query;

import java.io.Serializable;

final class FindOneAction<T extends Identifiable<Integer>> implements Query<Repository<T>, Option<T>>, Serializable {

    private static final long serialVersionUID = 1L;

    private final int id;

    FindOneAction(int id) {
        this.id = id;
    }

    @Override
    public Option<T> evaluate(Repository<T> system) {
        return system.findOne(id);
    }
}
