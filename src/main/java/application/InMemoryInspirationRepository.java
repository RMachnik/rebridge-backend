package application;

import domain.Inspiration;
import domain.InspirationRepository;
import io.vavr.control.Try;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryInspirationRepository implements InspirationRepository {

    final Map<String, Inspiration> inspirations = new ConcurrentHashMap<>();

    @Override
    public Optional<Inspiration> findById(String inspirationId) {
        return Optional.ofNullable(inspirations.get(inspirationId));
    }

    @Override
    public Try<Inspiration> save(Inspiration inspiration) {
        return Try.of(() -> inspirations.put(inspiration.getId(), inspiration));
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException();
    }
}
