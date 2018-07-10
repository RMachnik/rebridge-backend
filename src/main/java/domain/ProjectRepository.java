package domain;

import io.vavr.control.Try;

import java.util.Optional;

public interface ProjectRepository {

    Try<Project> save(Project project);

    Optional<Project> findById(String projectId);

    void delete(String projectId);
}
