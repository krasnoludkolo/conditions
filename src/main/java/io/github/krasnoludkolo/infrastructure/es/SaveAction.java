package io.github.krasnoludkolo.infrastructure.es;

import io.github.krasnoludkolo.infrastructure.Identifiable;
import io.github.krasnoludkolo.infrastructure.Repository;
import pl.setblack.airomem.core.Command;

import java.io.Serializable;

final class SaveAction<T extends Identifiable<Integer>> implements Command<Repository<T>, T>, Serializable {

    private static final long serialVersionUID = 1L;

    private final T toSave;

    SaveAction(T toSave) {
        this.toSave = toSave;
    }

    @Override
    public T execute(Repository<T> system) {
        return system.save(toSave);
    }
}
