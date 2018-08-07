package domain.project;

import application.dto.QuestionnaireAnswersDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@UserDefinedType
@Data
@AllArgsConstructor
public class Questionnaire implements WithId<UUID> {

    UUID id;
    List<Question> questions;

    public static Questionnaire create(List<String> questions) {
        return new Questionnaire(UUID.randomUUID(), createNotAnsweredQuestions(questions));
    }

    private static List<Question> createNotAnsweredQuestions(List<String> questions) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        return questions
                .stream()
                .map(question -> Question.notAnswered(atomicInteger.incrementAndGet(), question))
                .collect(toList());
    }

    public void fillInAnswers(QuestionnaireAnswersDto answersDto) {
        answersDto.getAnswers().forEach(
                answer -> {
                    Question question = findById(answer.getId());
                    Question answered = question.answer(answer.getAnswer());
                    questions.remove(question);
                    questions.add(answered);
                }
        );
    }

    private Question findById(int id) {
        return questions.stream()
                .filter(question -> question.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DomainExceptions.MissingQuestion(format("question %s is missing", id)));
    }
}
