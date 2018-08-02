package infrastructure.springData;

import domain.survey.QuestionnaireTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface QuestionnaireCrudRepository extends CrudRepository<QuestionnaireTemplate, UUID> {
}
