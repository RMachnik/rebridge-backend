package application.service;

import application.dto.QuestionnaireAnswersDto;
import domain.project.Project;
import domain.project.Questionnaire;
import lombok.Value;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Value
public class QuestionnaireService {

    ProjectService projectService;

    public Optional<Questionnaire> get(String userId, String projectId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        return ofNullable(project.getDetails().getQuestionnaire());
    }

    public Questionnaire answer(String userId, String projectId, QuestionnaireAnswersDto answerDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Questionnaire questionnaire = project.getDetails().getQuestionnaire();
        questionnaire.fillInAnswers(answerDto);

        projectService.save(project);
        return questionnaire;
    }
}
