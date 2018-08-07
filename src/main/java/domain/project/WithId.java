package domain.project;

import java.io.Serializable;

public interface WithId<T> extends Serializable {
    T getId();
}
