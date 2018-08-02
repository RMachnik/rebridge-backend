package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SurveyTemplateDto {
    String id;
    String name;
    List<String> questions;
}
