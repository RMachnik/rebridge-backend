package domain;

import io.vavr.control.Try;

import java.util.Optional;
import java.util.UUID;

public interface DomainRepository<T> {

    Optional<T> findById(UUID id);

    Try<T> save(T entity);

    void deleteById(UUID id);
}
