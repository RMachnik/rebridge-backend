package infrastructure.memory;

import domain.project.Project;
import domain.project.ProjectRepository;
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
    public void deleteById(UUID projectId) {
        projects.remove(projectId);
    }

}
