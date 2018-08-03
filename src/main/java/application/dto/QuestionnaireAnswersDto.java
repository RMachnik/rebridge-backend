package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionnaireAnswersDto {
    List<AnswerDto> answers;

    @Data
    @AllArgsConstructor
    public static class AnswerDto {
        int id;
        String answer;
    }
}
