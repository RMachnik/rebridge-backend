package application.service;

import application.dto.CreateOrUpdateInspirationDto;
import domain.project.Inspiration;
import domain.project.Project;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class InspirationService {

    ProjectService projectService;
    ImageService imageService;

    public List<Inspiration> findAll(String userId, String projectId) {
        Project project = projectService
                .findByUserIdAndProjectId(userId, projectId);

        return project.getInspirations();
    }

    public Inspiration findById(String userId, String projectId, String inspirationId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        return project.findInspiration(UUID.fromString(inspirationId));

    }

    public Inspiration create(String userId, String projectId, CreateOrUpdateInspirationDto inspirationDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);

        Inspiration inspiration = project.addInspiration(inspirationDto);
        projectService.save(project);

        return inspiration;
    }

    public Inspiration update(String userId, String projectId, String inspirationId, CreateOrUpdateInspirationDto inspirationDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);

        Inspiration updatedInspiration = project.updateInspiration(inspirationId, inspirationDto);
        projectService.save(project);
        return updatedInspiration;
    }

    public void delete(String userId, String projectId, String inspirationId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Inspiration removed = project.removeInspiration(UUID.fromString(inspirationId));

        if (removed.getDetails().getImageId() != null) {
            imageService.delete(removed.getDetails().getImageId());
        }

        projectService.save(project);
    }

}
