package domain;

import java.util.Optional;

public interface ProjectRepository {

    Optional<Project> findById(String projectId);

    Optional<Project> add(Project build);

    String delete(String projectId);

    Optional<Project> update(String projectId, Project build);
}
