package infrastructure.springData;

import domain.project.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ImageCrudRepository extends CrudRepository<Image, UUID> {
}
