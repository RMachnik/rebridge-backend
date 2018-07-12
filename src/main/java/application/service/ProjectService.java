package application.service;

import application.dto.ProjectDto;
import application.service.RepositoryExceptions.ProjectRepositoryException;
import domain.Project;
import domain.ProjectRepository;
import domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.Optional;

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
                .map(id -> projectRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    public Project findByUserIdAndProjectId(String userId, String projectId) {
        User user = userService.findById(userId);
        user.checkUser(projectId);
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectRepositoryException(format("unable to load project %s", projectId)));
    }

    public Project create(String userId, String projectName) {
        User user = userService.findById(userId);

        Project project = Project.create(projectName);

        Project addedProject = save(project);

        user.getProjectIds().add(addedProject.getId());
        userService.update(user);
        return addedProject;

    }


    public Project update(String userId, ProjectDto projectDto) {
        User user = userService.findById(userId);
        user.checkUser(projectDto.getId());

        Project existingProject = retrieveProjectById(projectDto.getId());
        Project updatedProject = existingProject.update(projectDto);

        save(updatedProject);
        return updatedProject;
    }

    public void remove(String userId, String projectId) {
        User user = userService.findById(userId);
        user.checkUser(projectId);

        user.removeProject(projectId);
        userService.update(user);
        //todo remove project if there are no users participating in this project
    }

    public Project retrieveProjectById(String projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectRepositoryException(format("there is no such project %s", projectId)));
    }

    Project save(Project project) {
        return projectRepository.save(project)
                .getOrElseThrow(ex -> new ProjectRepositoryException(format("problem with creating project %s", project.getId()), ex));
    }

}
