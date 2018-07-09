package application;

import domain.Inspiration;
import domain.InspirationRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryInspirationRepository implements InspirationRepository {
    @Override
    public List<Inspiration> findAll(String id, String projectId) {
        return null;
    }

    @Override
    public Optional<String> add(String projectId, Inspiration build) {
        return Optional.empty();
    }

    @Override
    public Optional<Inspiration> update(Inspiration inspiration) {
        return Optional.empty();
    }
}
