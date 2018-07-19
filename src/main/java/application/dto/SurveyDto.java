package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@AllArgsConstructor
public class SurveyDto {
    List<QuestionDto> questions;

    @Value
    static class QuestionDto {
        int questionId;
        String question;
        String answer;
    }
}
