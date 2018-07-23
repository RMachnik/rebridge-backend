package infrastructure.springData;

import domain.project.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProjectCrudRepository extends CrudRepository<Project, UUID> {
}
