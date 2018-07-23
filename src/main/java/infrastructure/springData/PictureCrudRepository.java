package infrastructure.springData;

import domain.project.Picture;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PictureCrudRepository extends CrudRepository<Picture, UUID> {
}
