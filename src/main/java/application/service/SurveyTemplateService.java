package application.service;

import application.dto.SurveyTemplateDto;
import domain.survey.SurveyTemplate;
import domain.survey.SurveyTemplateRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class SurveyTemplateService {

    SurveyTemplateRepository surveyTemplateRepository;

    public List<SurveyTemplate> findAll() {
        return surveyTemplateRepository.findAll();
    }

    public SurveyTemplate create(SurveyTemplateDto surveyTemplateDto) {
        return surveyTemplateRepository.save(SurveyTemplate.create(surveyTemplateDto))
                .getOrElseThrow(() -> new RepositoryExceptions.SurveyTemplateRepositoryException(String.format("unable to createWithRoleArchitect survey template %s", surveyTemplateDto)));
    }

    public SurveyTemplate update(UUID templateId, SurveyTemplateDto surveyTemplateDto) {
        SurveyTemplate surveyTemplate = surveyTemplateRepository.findById(templateId)
                .orElseThrow(() -> new RepositoryExceptions.SurveyTemplateRepositoryException(String.format("unable to find survey template %s", templateId)));
        surveyTemplate.setQuestions(surveyTemplateDto.getQuestions());
        surveyTemplateRepository.save(surveyTemplate);
        return surveyTemplate;
    }

    public void delete(UUID templateId) {
        surveyTemplateRepository.deleteById(templateId);
    }
}
