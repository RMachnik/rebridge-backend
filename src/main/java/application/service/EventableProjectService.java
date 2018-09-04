package application.service;

import application.dto.CreateProjectDto;
import application.dto.ProjectDto;
import domain.event.ChangeEvent;
import domain.project.Project;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class EventableProjectService implements ProjectService {

    ProjectService projectService;
    ChangeEventService changeEventService;

    @Override
    public List<Project> findAllByUserId(String userId) {
        return projectService.findAllByUserId(userId);
    }

    @Override
    public Project findByUserIdAndProjectId(String userId, String projectId) {
        return projectService.findByUserIdAndProjectId(userId, projectId);
    }

    @Override
    public Project create(String userId, CreateProjectDto createProjectDto) {
        return projectService.create(userId, createProjectDto);
    }

    @Override
    public Project update(String userId, ProjectDto projectDto) {
        Project project = projectService.update(userId, projectDto);
        changeEventService.publish(ChangeEvent.create(UUID.fromString(userId), project.getId(), String.format("Project %s was updated.", project.getName())));
        return project;
    }

    @Override
    public void delete(String userId, String projectId) {
        projectService.delete(userId, projectId);
    }

    @Override
    public Project retrieveProjectById(String projectId) {
        return projectService.retrieveProjectById(projectId);
    }

    @Override
    public Project save(Project project) {
        return projectService.save(project);
    }
}
