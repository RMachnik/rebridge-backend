package application.service;

import application.dto.CreateProjectDto;
import application.dto.ProjectDto;
import application.service.RepositoryExceptions.ProjectRepositoryException;
import domain.project.DomainExceptions;
import domain.project.Project;
import domain.project.ProjectRepository;
import domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Value
public class ProjectService {

    UserService userService;
    @Getter(value = AccessLevel.PRIVATE)
    ProjectRepository projectRepository;

    public List<Project> findAllByUserId(String userId) {
        return userService.findById(userId).getProjectIds()
                .stream()
                .map(projectRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    public Project findByUserIdAndProjectId(String userId, String projectId) {
        User user = userService.findById(userId);
        canUpdateProject(user, projectId);
        return projectRepository.findById(UUID.fromString(projectId))
                .orElseThrow(() -> new ProjectRepositoryException(format("unable to load project %s", projectId)));
    }

    private void canUpdateProject(User user, String projectId) {
        if (!user.canUpdateProject(UUID.fromString(projectId))) {
            new DomainExceptions.UserActionNotAllowed(format("user %s is not allowed see this project %s", user.getId(), projectId));
        }
    }

    public Project create(String userId, CreateProjectDto createProjectDto) {
        User user = userService.findById(userId);
        Project project = user.createProject(createProjectDto, projectRepository);
        userService.update(user);
        return project;

    }


    public Project update(String userId, ProjectDto projectDto) {
        User user = userService.findById(userId);
        canUpdateProject(user, projectDto.getId());

        Project existingProject = retrieveProjectById(projectDto.getId());
        Project updatedProject = existingProject.update(projectDto);

        save(updatedProject);
        return updatedProject;
    }

    public void remove(String userId, String projectId) {
        User user = userService.findById(userId);
        canUpdateProject(user, projectId);

        user.removeProject(UUID.fromString(projectId));
        userService.update(user);
        //todo remove project if there are no users participating in this project
    }

    public Project retrieveProjectById(String projectId) {
        return projectRepository.findById(UUID.fromString(projectId))
                .orElseThrow(() -> new ProjectRepositoryException(format("there is no such project %s", projectId)));
    }

    Project save(Project project) {
        return projectRepository.save(project)
                .getOrElseThrow(ex -> new ProjectRepositoryException(format("problem with creating project %s", project.getId()), ex));
    }

}
