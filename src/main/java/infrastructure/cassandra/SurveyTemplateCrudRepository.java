package infrastructure.cassandra;

import domain.survey.SurveyTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SurveyTemplateCrudRepository extends CrudRepository<SurveyTemplate, UUID> {
}
