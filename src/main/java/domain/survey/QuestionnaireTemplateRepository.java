package domain.survey;

import domain.project.DomainRepository;

import java.util.List;

public interface QuestionnaireTemplateRepository extends DomainRepository<QuestionnaireTemplate> {

    List<QuestionnaireTemplate> findAll();
}
