package io.github.krasnoludkolo.infrastructure.es;

import io.github.krasnoludkolo.infrastructure.Identifiable;
import io.github.krasnoludkolo.infrastructure.Repository;
import io.github.krasnoludkolo.resolver.Success;
import pl.setblack.airomem.core.Command;

import java.io.Serializable;

final class DeleteAction<T extends Identifiable<Integer>> implements Command<Repository<T>, Success>, Serializable {

    private static final long serialVersionUID = 1L;

    private final int id;

    DeleteAction(int id) {
        this.id = id;
    }

    @Override
    public Success execute(Repository<T> system) {
        return system.delete(id);
    }
}
