package application.service;

import application.dto.QuestionnaireTemplateDto;
import domain.RepositoryExceptions.QuestionnaireTemplateRepositoryException;
import domain.survey.QuestionnaireTemplate;
import domain.survey.QuestionnaireTemplateRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@AllArgsConstructor
public class QuestionnaireTemplateService {

    QuestionnaireTemplateRepository questionnaireTemplateRepository;

    public List<QuestionnaireTemplate> findAll() {
        return questionnaireTemplateRepository.findAll();
    }

    public Optional<QuestionnaireTemplate> findById(String templateId) {
        return questionnaireTemplateRepository.findById(UUID.fromString(templateId));
    }

    public QuestionnaireTemplate create(QuestionnaireTemplateDto questionnaireTemplateDto) {
        return questionnaireTemplateRepository.save(QuestionnaireTemplate.create(questionnaireTemplateDto))
                .getOrElseThrow(
                        () -> new QuestionnaireTemplateRepositoryException(format("unable to createWithRoleArchitect questionnaire template %s", questionnaireTemplateDto))
                );
    }

    public QuestionnaireTemplate update(UUID templateId, QuestionnaireTemplateDto questionnaireTemplateDto) {
        QuestionnaireTemplate questionnaireTemplate = questionnaireTemplateRepository.findById(templateId)
                .orElseThrow(() -> new QuestionnaireTemplateRepositoryException(format("unable to find questionnaire template %s", templateId)));
        QuestionnaireTemplate updated = questionnaireTemplate.updateQuestions(questionnaireTemplateDto.getQuestions());

        return questionnaireTemplateRepository.save(updated)
                .getOrElseThrow(() -> new QuestionnaireTemplateRepositoryException(format("Unable to update questionnaire %s", templateId)));
    }

    public void delete(UUID templateId) {
        questionnaireTemplateRepository.deleteById(templateId);
    }
}
