package infrastructure.cassandra;

import com.google.common.collect.Lists;
import domain.survey.SurveyTemplate;
import domain.survey.SurveyTemplateRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class CassandraSurveyTemplateRepository implements SurveyTemplateRepository {

    SurveyTemplateCrudRepository surveyTemplateCrudRepository;

    @Override
    public Optional<SurveyTemplate> findById(UUID id) {
        return surveyTemplateCrudRepository.findById(id);
    }

    @Override
    public Try<SurveyTemplate> save(SurveyTemplate entity) {
        return Try.of(() -> surveyTemplateCrudRepository.save(entity));
    }

    @Override
    public void deleteById(UUID id) {
        surveyTemplateCrudRepository.deleteById(id);
    }

    public List<SurveyTemplate> findAll() {
        return Lists.newArrayList(surveyTemplateCrudRepository.findAll());
    }
}
