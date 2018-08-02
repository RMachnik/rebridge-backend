package application.service;

import application.dto.*;
import domain.project.Details;
import domain.project.DomainExceptions;
import domain.project.Project;
import domain.project.Questionnaire;
import domain.survey.QuestionnaireTemplate;
import domain.user.EmailAddress;
import domain.user.User;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static application.dto.DtoAssemblers.fromInformationToDto;
import static java.util.stream.Collectors.toList;

@Value
public class ProjectDetailsService {

    UserService userService;
    ProjectService projectService;
    QuestionnaireTemplateServices questionnaireTemplateServices;

    public ProjectDetailsDto get(String userId, String projectId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Details details = project.getDetails();

        List<InvestorDto> investors =
                details.getInvestorEmailAddresses()
                        .stream()
                        .map(EmailAddress::getValue)
                        .map(mapInvestorOrEmail())
                        .collect(toList());

        ProjectDetailsDto projectDetailsDto = fromInformationToDto(details, investors);
        return projectDetailsDto;
    }

    private Function<String, InvestorDto> mapInvestorOrEmail() {
        return email -> {

            Optional<User> possibleUser = userService.tryFindUserByEmail(email);
            if (possibleUser.isPresent()) {
                return DtoAssemblers.fromUserToInvestor(possibleUser.get());
            } else {
                return InvestorDto.builder()
                        .email(email)
                        .build();
            }
        };
    }


    public CreateUpdateProjectDetailsDto create(String userId, String projectId, CreateUpdateProjectDetailsDto projectDetailsDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        String surveyTemplateId = projectDetailsDto.getSurveyTemplateId();
        QuestionnaireTemplate questionnaireTemplate = questionnaireTemplateServices.findById(surveyTemplateId)
                .orElseThrow(() -> new DomainExceptions.MissingQuestionnaireTemplate(String.format("missing questionnaire template %s", surveyTemplateId)));


        project.createDetails(projectDetailsDto, Questionnaire.create(questionnaireTemplate.getQuestions()));
        projectService.save(project);

        return projectDetailsDto;
    }

    private List<User> findInvestors(List<String> investors) {
        return investors.stream()
                .map((email) -> createIfDoesNotExists(email))
                .collect(toList());
    }

    private User createIfDoesNotExists(String email) {
        return userService.tryFindUserByEmail(email)
                .orElseGet(() -> userService.createWithRoleInvestor(email));
    }

    public CreateUpdateProjectDetailsDto update(String userId, String projectId, CreateUpdateProjectDetailsDto updateProjectDetailsDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);

        Details details = project.getDetails();
        Details updated = details.update(updateProjectDetailsDto);

        project.setDetails(updated);

        projectService.save(project);
        return updateProjectDetailsDto;
    }

    public Set<EmailAddress> addInvestors(CurrentUser currentUser, String projectId, AddInvestorDto addInvestorDto) {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        List<User> investors = findInvestors(addInvestorDto.getInvestorEmails());
        investors.stream()
                .forEach(user -> {
                            user.addProject(project.getId());
                            userService.update(user);
                            userService.sendInvitation(user);
                        }
                );
        project.getDetails().addInvestors(investors);
        projectService.save(project);
        return project.getDetails().getInvestorEmailAddresses();
    }

    public Set<EmailAddress> removeInvestors(CurrentUser currentUser, String projectId, AddInvestorDto removeInvestors) {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        List<User> investors = findInvestors(removeInvestors.getInvestorEmails());
        investors.stream()
                .forEach(user -> {
                            user.removeProject(project.getId());
                            userService.update(user);
                        }
                );
        project.getDetails().removeInvestors(investors);
        return project.getDetails().getInvestorEmailAddresses();
    }
}
