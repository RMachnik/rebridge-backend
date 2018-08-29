package domain.event;

import domain.project.DomainRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ChangeEventRepository extends DomainRepository<ChangeEvent> {

    List<ChangeEvent> findAllExcludingOwnForProvidedProjects(UUID userId, Collection<UUID> projectIds);

}
