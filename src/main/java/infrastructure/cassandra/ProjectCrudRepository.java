package infrastructure.cassandra;

import domain.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProjectCrudRepository extends CrudRepository<Project, UUID> {
}
