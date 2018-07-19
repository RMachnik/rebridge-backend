package application.service;

import application.dto.SurveyAnswersDto;
import domain.project.Project;
import domain.project.Survey;
import lombok.Value;

@Value
public class SurveyService {

    ProjectService projectService;

    public Survey get(String userId, String projectId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        return project.getDetails().getSurvey();
    }

    public Survey answer(String userId, String projectId, SurveyAnswersDto answerDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Survey survey = project.getDetails().getSurvey();
        survey.fillInAnswers(answerDto);

        projectService.save(project);
        return survey;
    }
}
