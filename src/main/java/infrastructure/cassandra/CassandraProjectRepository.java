package infrastructure.cassandra;

import domain.Project;
import domain.ProjectRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class CassandraProjectRepository implements ProjectRepository {

    ProjectCrudRepository projectCrudRepository;

    @Override
    public Optional<Project> findById(UUID id) {
        return projectCrudRepository.findById(id);
    }

    @Override
    public Try<Project> save(Project entity) {
        return Try.of(() -> projectCrudRepository.save(entity));
    }

    @Override
    public void deleteById(UUID id) {
        projectCrudRepository.deleteById(id);
    }
}
