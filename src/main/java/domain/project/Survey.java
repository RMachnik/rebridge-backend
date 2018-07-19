package domain.project;

import application.dto.SurveyAnswersDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@UserDefinedType
@Data
@AllArgsConstructor
public class Survey {
    List<Question> questions;

    public static Survey init(List<String> questions) {
        return new Survey(questions
                .stream()
                .map(q -> new Question(q, EMPTY))
                .collect(toList()));
    }

    public void fillInAnswers(SurveyAnswersDto answersDto) {
        answersDto.getAnswers().stream()
                .forEach(answerDto -> {
                    questions.get(answerDto.getQuestionId())
                            .update(answerDto.getAnswer());
                });
    }
}
