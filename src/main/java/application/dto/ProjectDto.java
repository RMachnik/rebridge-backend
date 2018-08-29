package application.dto;

import domain.project.Project;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
    String id;
    String name;
    String questionnaireTemplateId;
    SimpleDetailsDto details;

    public static ProjectDto create(Project project) {
        return builder()
                .id(project.getId().toString())
                .name(project.getName())
                .questionnaireTemplateId(project.getQuestionnaireTemplateId().toString())
                .details(ProjectDetailsDto.create(project.getDetails()))
                .build();
    }
}
