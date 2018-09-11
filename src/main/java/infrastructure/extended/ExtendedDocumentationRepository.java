package infrastructure.extended;

import domain.project.Documentation;
import domain.project.DocumentationRepository;
import infrastructure.springData.DocumentationCrudRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ExtendedDocumentationRepository implements DocumentationRepository {

    DocumentationCrudRepository documentationCrudRepository;

    @Override
    public Optional<Documentation> findById(UUID id) {
        return documentationCrudRepository.findById(id);
    }

    @Override
    public Try<Documentation> save(Documentation entity) {
        return Try.of(() -> documentationCrudRepository.save(entity));
    }

    @Override
    public void deleteById(UUID id) {
        documentationCrudRepository.deleteById(id);
    }

    @Override
    public Optional<Documentation> findByProject(String projectId) {
        return Optional.ofNullable(documentationCrudRepository.findByProjectId(UUID.fromString(projectId)));
    }
}
