package infrastructure.springData;

import domain.event.ChangeEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ChangeEventCrudRepository extends CrudRepository<ChangeEvent, UUID> {

}
