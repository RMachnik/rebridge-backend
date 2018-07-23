package infrastructure.extended;

import domain.project.Project;
import domain.project.ProjectRepository;
import infrastructure.springData.ProjectCrudRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ExtendedProjectRepository implements ProjectRepository {

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
