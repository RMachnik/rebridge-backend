package application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
    String id;
    String name;
    String questionnaireTemplateId;
    SimpleDetailsDto details;
}
