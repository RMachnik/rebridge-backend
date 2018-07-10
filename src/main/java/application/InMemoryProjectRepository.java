package application;

import domain.Project;
import domain.ProjectRepository;
import io.vavr.control.Try;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProjectRepository implements ProjectRepository {

    Map<String, Project> projects = new ConcurrentHashMap<>();

    @Override
    public Optional<Project> findById(String projectId) {
        return Optional.ofNullable(projects.get(projectId));
    }

    @Override
    public Try<Project> save(Project project) {
        return Try.of(() -> projects.put(project.getId(), project));
    }

    @Override
    public void delete(String projectId) {
        projects.remove(projectId);
    }

}
