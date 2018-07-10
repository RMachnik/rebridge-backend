package domain;

import java.util.List;
import java.util.Optional;

public interface InspirationRepository {

    List<Inspiration> findAll(String projectId);

    Optional<String> add(String projectId, Inspiration inspiration);

    Optional<Inspiration> update(Inspiration inspiration);
}
