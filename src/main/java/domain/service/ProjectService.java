package domain.service;

import domain.Project;
import domain.ProjectRepository;
import domain.User;
import domain.UserRepository;
import domain.service.DomainExceptions.ProjectRepositoryException;
import domain.service.DomainExceptions.UserRepositoryException;
import lombok.Value;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Collections.EMPTY_LIST;

@Value
public class ProjectService {

    UserRepository userRepository;
    ProjectRepository projectRepository;

    public List<Project> findByUserId(String userId) {
        return userRepository.findById(userId)
                .map(user -> user.getProjects())
                .orElse(EMPTY_LIST);
    }

    public Project findByUserIdAndProjectId(String userId, String projectId) {
        return findByUserId(userId)
                .stream()
                .filter(project -> project.getId().equals(projectId))
                .findFirst()
                .orElseThrow(() -> new ProjectRepositoryException(format("unable to load project %id", projectId)));
    }

    public String create(String userId, String projectName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserRepositoryException(format("user with id %s is missing", userId)));

        Project project = Project.builder()
                .name(projectName)
                .id(UUID.randomUUID().toString())
                .build();

        Project addedProject = projectRepository.save(project)
                .getOrElseThrow(ex -> new ProjectRepositoryException(format("problem with creating project %s", projectName), ex));

        user.getProjects().add(addedProject);
        return userRepository.save(user)
                .mapTry(User::getId)
                .getOrElseThrow(ex ->
                        new ProjectRepositoryException(format("problem with adding project %s to %s", project.getName(), userId), ex));

    }

    public void remove(String userId, String projectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserRepositoryException(format("user with id %s is missing", userId)));

        Project toBeRemoved = user.getProjects().stream()
                .filter(project -> project.getId().equals(projectId))
                .findFirst()
                .orElseThrow(() -> new ProjectRepositoryException(format("there is no such project %s for user %s", projectId, userId)));

        user.getProjects().remove(toBeRemoved);
        userRepository.save(user)
                .getOrElseThrow((ex) -> new UserRepositoryException(format("unable to update user %s", userId), ex));

        //todo remove project if there are no users participating in this project
    }

}
