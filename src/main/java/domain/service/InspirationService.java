package domain.service;

import application.rest.controllers.dto.InspirationDto;
import domain.Inspiration;
import domain.Project;
import lombok.Value;

import java.util.List;

@Value
public class InspirationService {

    ProjectService projectService;

    public List<Inspiration> findAll(String userId, String projectId) {
        Project project = projectService
                .findByUserIdAndProjectId(userId, projectId);

        return project.getInspirations();
    }

    public Inspiration findById(String userId, String projectId, String inspirationId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        return project.findInspiration(inspirationId);

    }

    public Inspiration create(String userId, String projectId, String inspirationName) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);

        Inspiration inspiration = project.addInspiration(inspirationName);
        projectService.save(project);

        return inspiration;
    }

    public Inspiration update(String userId, String projectId, InspirationDto inspirationDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);

        Inspiration updatedInspiration = project.updateInspiration(inspirationDto);
        projectService.save(project);
        return updatedInspiration;
    }

    public void delete(String userId, String projectId, String inspirationId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        project.removeInspiration(inspirationId);

        projectService.save(project);
    }

}
