package infrastructure.extended;

import com.google.common.collect.Lists;
import domain.survey.QuestionnaireTemplate;
import domain.survey.QuestionnaireTemplateRepository;
import infrastructure.springData.QuestionnaireCrudRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ExtendedQuestionnaireTemplateRepository implements QuestionnaireTemplateRepository {

    QuestionnaireCrudRepository questionnaireCrudRepository;

    @Override
    public Optional<QuestionnaireTemplate> findById(UUID id) {
        return questionnaireCrudRepository.findById(id);
    }

    @Override
    public Try<QuestionnaireTemplate> save(QuestionnaireTemplate entity) {
        return Try.of(() -> questionnaireCrudRepository.save(entity));
    }

    @Override
    public void deleteById(UUID id) {
        questionnaireCrudRepository.deleteById(id);
    }

    public List<QuestionnaireTemplate> findAll() {
        return Lists.newArrayList(questionnaireCrudRepository.findAll());
    }
}
