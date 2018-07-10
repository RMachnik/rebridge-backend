package domain;

import io.vavr.control.Try;

import java.util.Optional;

public interface InspirationRepository {

    Try<Inspiration> save(Inspiration inspiration);

    Optional<Inspiration> findById(String inspirationId);

}
