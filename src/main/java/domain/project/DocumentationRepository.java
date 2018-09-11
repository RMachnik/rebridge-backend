package domain.project;

import domain.DomainRepository;

import java.util.Optional;
import java.util.UUID;

public interface DocumentationRepository extends DomainRepository<Documentation> {
    Optional<Documentation> findByProject(UUID projectId);
}
