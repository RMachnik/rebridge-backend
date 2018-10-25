package application.dto;

import domain.project.Project;
import lombok.Builder;
import lombok.Data;

import static java.util.Optional.ofNullable;

@Data
@Builder
public class ProjectDto {
    String id;
    String name;
    String catalogueId;
    SimpleDetailsDto details;

    public static ProjectDto create(Project project) {
        return builder()
                .id(project.getId().toString())
                .name(project.getName())
                .details(ofNullable(project.getDetails()).map(ProjectDetailsDto::create).orElse(null))
                .catalogueId(project.getCatalogue().getId().toString())
                .build();
    }
}
