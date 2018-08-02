package application.service;

import application.dto.QuestionnaireTemplateDto;
import application.service.RepositoryExceptions.QuestionnaireTemplateRepositoryException;
import domain.survey.QuestionnaireTemplate;
import domain.survey.QuestionnaireTemplateRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class QuestionnaireTemplateServices {

    QuestionnaireTemplateRepository questionnaireTemplateRepository;

    public List<QuestionnaireTemplate> findAll() {
        return questionnaireTemplateRepository.findAll();
    }

    public Optional<QuestionnaireTemplate> findById(String templateId) {
        return questionnaireTemplateRepository.findById(UUID.fromString(templateId));
    }

    public QuestionnaireTemplate create(QuestionnaireTemplateDto questionnaireTemplateDto) {
        return questionnaireTemplateRepository.save(QuestionnaireTemplate.create(questionnaireTemplateDto))
                .getOrElseThrow(() -> new QuestionnaireTemplateRepositoryException(String.format("unable to createWithRoleArchitect questionnaire template %s", questionnaireTemplateDto)));
    }

    public QuestionnaireTemplate update(UUID templateId, QuestionnaireTemplateDto questionnaireTemplateDto) {
        QuestionnaireTemplate questionnaireTemplate = questionnaireTemplateRepository.findById(templateId)
                .orElseThrow(() -> new QuestionnaireTemplateRepositoryException(String.format("unable to find questionnaire template %s", templateId)));
        questionnaireTemplate.setQuestions(questionnaireTemplateDto.getQuestions());
        questionnaireTemplateRepository.save(questionnaireTemplate);
        return questionnaireTemplate;
    }

    public void delete(UUID templateId) {
        questionnaireTemplateRepository.deleteById(templateId);
    }
}
