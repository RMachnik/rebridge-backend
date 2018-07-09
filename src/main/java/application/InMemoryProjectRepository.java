package application;

import domain.Project;
import domain.ProjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryProjectRepository implements ProjectRepository {

    List<Project> projects = new ArrayList<>();

    @Override
    public Optional<Project> findById(String projectId) {
        return projects.stream()
                .filter(project -> project.getId().equals(projectId))
                .findFirst();
    }

    @Override
    public Optional<Project> add(Project project) {
        String uuid = UUID.randomUUID().toString();
        Project brandNewProject = Project.builder().id(uuid).name(project.getName()).build();
        projects.add(brandNewProject);
        return Optional.of(brandNewProject);
    }

    @Override
    public String delete(String projectId) {
        projects.stream()
                .filter(project -> project.getId().equals(projectId))
                .findFirst()
                .map(forRemove -> projects.remove(forRemove))
                .orElseThrow(RuntimeException::new);
        return projectId;
    }

    @Override
    public Optional<Project> update(String projectId, Project project) {
        return Optional.empty();
    }
}
