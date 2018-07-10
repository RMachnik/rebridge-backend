package domain.service;

import domain.Inspiration;
import domain.InspirationRepository;
import domain.Project;
import domain.ProjectRepository;
import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

@Value
public class InspirationService {

    ProjectRepository projectRepository;
    InspirationRepository inspirationRepository;

    public List<Inspiration> findInspirations(String projectId) {
        return projectRepository
                .findById(projectId)
                .map(Project::getInspirations)
                .orElse(Collections.EMPTY_LIST);
    }

    public String create(String projectId, String inspirationName) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new DomainExceptions.MissingProjectException(format("can't find project %s", projectId)));

        Inspiration inspiration = Inspiration.builder()
                .id(UUID.randomUUID().toString())
                .name(inspirationName)
                .build();

        return inspirationRepository.save(inspiration)
                .andThen(ins -> project.getInspirations().add(ins))
                .map(ins -> projectRepository.save(project))
                .map(p -> inspiration.getId())
                .getOrElseThrow(
                        ex -> new DomainExceptions.AddingInspirationException(format("can't save inspiration %s to project %s", projectId, inspirationName), ex)
                );
    }

    public void delete(String projectId, String inspirationId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new DomainExceptions.MissingProjectException(format("missing project %s", projectId)));

        Inspiration toBeRemoved = project.getInspirations()
                .stream()
                .filter(inspiration -> inspiration.getId().equals(inspirationId))
                .findAny()
                .orElseThrow(() -> new DomainExceptions.MissingInspirationException(format("missing inspiration %s", inspirationId)));

        project.getInspirations().remove(toBeRemoved);
        projectRepository.save(project);
    }
}
