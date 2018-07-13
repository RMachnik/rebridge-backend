package infrastructure;

import infrastructure.entity.ProjectEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProjectCrudRepository extends CrudRepository<ProjectEntity, UUID> {
}
