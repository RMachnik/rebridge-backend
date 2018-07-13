package infrastructure;

import domain.Project;
import domain.ProjectRepository;
import io.vavr.control.Try;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProjectRepository implements ProjectRepository {

    final Map<UUID, Project> projects = new ConcurrentHashMap<>();

    @Override
    public Optional<Project> findById(UUID projectId) {
        return Optional.ofNullable(projects.get(projectId));
    }

    @Override
    public Try<Project> save(Project project) {
        return Try.of(() -> {
            projects.put(project.getId(), project);
            return project;
        });
    }

    @Override
    public void delete(UUID projectId) {
        projects.remove(projectId);
    }

}
