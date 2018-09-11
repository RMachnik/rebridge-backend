package domain.survey;

import domain.DomainRepository;

import java.util.List;

public interface QuestionnaireTemplateRepository extends DomainRepository<QuestionnaireTemplate> {

    List<QuestionnaireTemplate> findAll();
}
