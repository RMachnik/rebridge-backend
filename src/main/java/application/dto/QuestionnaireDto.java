package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionnaireDto {
    List<QuestionDto> questions;

    @Value
    static class QuestionDto {
        int id;
        String question;
        String answer;
    }
}
