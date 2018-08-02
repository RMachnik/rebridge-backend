package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProjectDto {
    String name;
    String questionnaireTemplateId;
}
