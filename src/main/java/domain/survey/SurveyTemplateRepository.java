package domain.survey;

import domain.project.DomainRepository;

import java.util.List;

public interface SurveyTemplateRepository extends DomainRepository<SurveyTemplate> {

    List<SurveyTemplate> findAll();
}
