package domain.project;

import domain.DomainRepository;

import java.util.UUID;

public interface DocumentationRepository extends DomainRepository<Documentation> {
    Documentation findByProject(UUID projectId);
}
