package application.dto;

import domain.project.Project;
import lombok.Builder;
import lombok.Data;

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
                .details(ProjectDetailsDto.create(project.getDetails()))
                .catalogueId(project.getCatalogue().getId().toString())
                .build();
    }
}
