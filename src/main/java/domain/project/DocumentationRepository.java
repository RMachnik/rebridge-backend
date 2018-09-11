package domain.project;

import domain.DomainRepository;

import java.util.Optional;

public interface DocumentationRepository extends DomainRepository<Documentation> {
    Optional<Documentation> findByProject(String projectId);
}
