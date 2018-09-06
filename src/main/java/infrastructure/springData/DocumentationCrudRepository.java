package infrastructure.springData;

import domain.project.Documentation;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DocumentationCrudRepository extends CrudRepository<Documentation, UUID> {

    @Query("Select * from documentations where projectId=?0")
    Documentation findByProjectId(String projectId);
}
