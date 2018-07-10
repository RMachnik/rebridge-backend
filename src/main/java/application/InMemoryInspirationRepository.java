package application;

import domain.Inspiration;
import domain.InspirationRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryInspirationRepository implements InspirationRepository {

    Map<String, List<Inspiration>> inspirations = new ConcurrentHashMap<>();

    @Override
    public List<Inspiration> findAll(String projectId) {
        return inspirations.get(projectId);
    }

    @Override
    public Optional<String> add(String projectId, Inspiration inspiration) {
        return Optional.empty();
    }

    @Override
    public Optional<Inspiration> update(Inspiration inspiration) {
        return Optional.empty();
    }
}
