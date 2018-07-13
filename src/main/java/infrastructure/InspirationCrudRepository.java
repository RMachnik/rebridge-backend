package infrastructure;

import infrastructure.entity.InspirationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface InspirationCrudRepository extends CrudRepository<InspirationEntity, UUID> {
}
