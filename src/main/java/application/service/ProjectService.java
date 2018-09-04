package application.service;

import application.dto.CreateProjectDto;
import application.dto.ProjectDto;
import domain.project.Project;

import java.util.List;

public interface ProjectService {

    List<Project> findAllByUserId(String userId);

    Project findByUserIdAndProjectId(String userId, String projectId);

    Project create(String userId, CreateProjectDto createProjectDto);

    Project update(String userId, ProjectDto projectDto);

    void delete(String userId, String projectId);

    Project retrieveProjectById(String projectId);

    Project save(Project project);
}
