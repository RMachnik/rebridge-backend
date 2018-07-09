package domain;

import java.util.List;
import java.util.Optional;

public interface InspirationRepository {

    List<Inspiration> findAll(String id, String projectId);

    Optional<String> add(String projectId, Inspiration build);

    Optional<Inspiration> update(Inspiration inspiration);
}
