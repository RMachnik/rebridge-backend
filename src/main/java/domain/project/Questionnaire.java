package domain.project;

import application.dto.QuestionnaireAnswersDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@UserDefinedType
@Data
@AllArgsConstructor
public class Questionnaire implements WithId<UUID> {
    UUID id;
    List<Question> questions;

    public static Questionnaire create(List<String> questions) {
        return new Questionnaire(UUID.randomUUID(), questions
                .stream()
                .map(q -> new Question(q, EMPTY))
                .collect(toList()));
    }

    public void fillInAnswers(QuestionnaireAnswersDto answersDto) {
        answersDto.getAnswers().stream()
                .forEach(answerDto ->
                        questions.get(answerDto.getQuestionId()).update(answerDto.getAnswer()));
    }
}
