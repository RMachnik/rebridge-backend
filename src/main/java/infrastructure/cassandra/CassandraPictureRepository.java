package infrastructure.cassandra;

import domain.Picture;
import domain.PictureRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class CassandraPictureRepository implements PictureRepository {

    PictureCrudRepository pictureCrudRepository;

    @Override
    public Optional<Picture> findById(UUID id) {
        return pictureCrudRepository.findById(id);
    }

    @Override
    public Try<Picture> save(Picture entity) {
        return Try.of(() -> pictureCrudRepository.save(entity));
    }

    @Override
    public void deleteById(UUID id) {
        pictureCrudRepository.deleteById(id);
    }
}
