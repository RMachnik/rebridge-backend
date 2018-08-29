package application.dto;

import domain.survey.QuestionnaireTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionnaireTemplateDto {
    String id;
    String name;
    List<String> questions;

    public static QuestionnaireTemplateDto create(QuestionnaireTemplate questionnaireTemplate) {
        return new QuestionnaireTemplateDto(questionnaireTemplate.getId().toString(), questionnaireTemplate.getName(), questionnaireTemplate.getQuestions());
    }
}
