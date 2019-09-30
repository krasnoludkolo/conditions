package io.github.krasnoludkolo.infrastructure;

import java.io.Serializable;

public interface Identifiable<T> extends Serializable {

    T getId();

}
