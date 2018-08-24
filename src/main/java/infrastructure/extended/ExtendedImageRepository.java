package infrastructure.extended;

import domain.project.Image;
import domain.project.ImageRepository;
import infrastructure.springData.ImageCrudRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ExtendedImageRepository implements ImageRepository {

    ImageCrudRepository imageCrudRepository;

    @Override
    public Optional<Image> findById(UUID id) {
        return imageCrudRepository.findById(id);
    }

    @Override
    public Try<Image> save(Image entity) {
        return Try.of(() -> imageCrudRepository.save(entity));
    }

    @Override
    public void deleteById(UUID id) {
        imageCrudRepository.deleteById(id);
    }
}
