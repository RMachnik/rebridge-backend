package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SurveyAnswersDto {
    List<AnswerDto> answers;

    @Data
    @AllArgsConstructor
    public static class AnswerDto {
        int questionId;
        String answer;
    }
}
