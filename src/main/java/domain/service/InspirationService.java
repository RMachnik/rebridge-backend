package domain.service;

import domain.*;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static domain.service.DomainExceptions.*;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Value
public class InspirationService {

    public static final InspirationDetail EMPTY_INSPIRATION_DETAIL = InspirationDetail.builder()
            .url(EMPTY)
            .picture(new byte[]{})
            .rating(0)
            .description(EMPTY)
            .comments(new ArrayList<>())
            .build();
    ProjectRepository projectRepository;
    InspirationRepository inspirationRepository;

    public List<Inspiration> findInspirations(String projectId) {
        return projectRepository
                .findById(projectId)
                .map(Project::getInspirations)
                .orElse(Collections.EMPTY_LIST);
    }

    public Inspiration getInspirationById(String inspirationId) {
        return inspirationRepository.findById(inspirationId)
                .orElseThrow(() -> new MissingInspirationException(format("missing inspiration %s", inspirationId)));

    }

    public Inspiration create(String projectId, String inspirationName) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectRepositoryException(format("can't findById project %s", projectId)));

        Inspiration inspiration = Inspiration.builder()
                .id(UUID.randomUUID().toString())
                .name(inspirationName)
                .inspirationDetail(EMPTY_INSPIRATION_DETAIL)
                .build();

        Inspiration saved = inspirationRepository.save(inspiration)
                .getOrElseThrow(
                        ex -> new InspirationRepositoryException(format("can't save inspiration %s to project %s", projectId, inspirationName), ex)
                );
        project.getInspirations().add(saved);

        projectRepository.save(project)
                .getOrElseThrow(
                        ex -> new ProjectRepositoryException(format("problem with saving project %s", project), ex)
                );

        return saved;

    }

    public void delete(String projectId, String inspirationId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectRepositoryException(format("missing project %s", projectId)));

        Inspiration toBeRemoved = project.getInspirations()
                .stream()
                .filter(inspiration -> inspiration.getId().equals(inspirationId))
                .findAny()
                .orElseThrow(() -> new MissingInspirationException(format("missing inspiration %s", inspirationId)));

        project.getInspirations().remove(toBeRemoved);
        projectRepository.save(project);
    }
}
