package infrastructure;

import infrastructure.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CommentCrudRepository extends CrudRepository<CommentEntity, UUID> {
}
