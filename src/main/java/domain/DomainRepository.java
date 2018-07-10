package domain;

import io.vavr.control.Try;

import java.util.Optional;

public interface DomainRepository<T extends Id<E>, E> {

    Optional<T> findById(E id);

    Try<T> save(T entity);

    void delete(E id);
}
