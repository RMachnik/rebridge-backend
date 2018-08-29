package application.service;

import application.dto.*;
import domain.project.Details;
import domain.project.DomainExceptions.MissingQuestionnaireTemplate;
import domain.project.Project;
import domain.project.Questionnaire;
import domain.survey.QuestionnaireTemplate;
import domain.user.EmailAddress;
import domain.user.User;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static application.dto.DtoAssemblers.fromInformationToDto;
import static application.dto.DtoAssemblers.fromUserToInvestor;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class ProjectDetailsService {

    UserService userService;
    ProjectService projectService;
    QuestionnaireTemplateService questionnaireTemplateService;

    public ProjectDetailsDto get(String userId, String projectId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Details details = project.getDetails();

        List<InvestorDto> investors =
                findInvestors(details);

        ProjectDetailsDto projectDetailsDto = fromInformationToDto(details, investors, projectId);
        return projectDetailsDto;
    }

    public List<InvestorDto> findInvestors(Details details) {
        return details.getInvestorEmailAddresses()
                .stream()
                .map(EmailAddress::getValue)
                .map(email -> getInvestor(email))
                .collect(toList());
    }

    private InvestorDto getInvestor(String email) {
        Optional<User> possibleUser = userService.tryFindUserByEmail(email);
        if (possibleUser.isPresent()) {
            return fromUserToInvestor(possibleUser.get());
        } else {
            return InvestorDto.builder()
                    .email(email)
                    .build();
        }
    }


    public Details create(String userId, String projectId, UpdateProjectDetailsDto updateProjectDetailsDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        String questionnaireId = project.getQuestionnaireTemplateId().toString();
        QuestionnaireTemplate questionnaireTemplate = questionnaireTemplateService.findById(questionnaireId)
                .orElseThrow(() -> new MissingQuestionnaireTemplate(format("missing questionnaire template %s", questionnaireId)));


        Details details = project.createDetails(updateProjectDetailsDto, Questionnaire.create(questionnaireTemplate.getQuestions()));
        projectService.save(project);

        return details;
    }

    private User findInvestors(EmailAddress emailAddress) {
        return createIfDoesNotExists(emailAddress.getValue());
    }

    private User createIfDoesNotExists(String email) {
        return userService.tryFindUserByEmail(email)
                .orElseGet(() -> userService.createWithRoleInvestor(email));
    }

    public ProjectDetailsDto update(String userId, String projectId, UpdateProjectDetailsDto updateProjectDetailsDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);


        project.updateDetails(updateProjectDetailsDto);

        projectService.save(project);
        return fromInformationToDto(project.getDetails(), findInvestors(project.getDetails()), projectId);
    }

    public Set<EmailAddress> addInvestor(CurrentUser currentUser, String projectId, AddInvestorDto addInvestorDto) {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        User investor = findInvestors(new EmailAddress(addInvestorDto.getEmail()));
        investor.addProject(project.getId());

        userService.update(investor);
        userService.sendInvitation(investor);

        project.getDetails().addInvestor(investor);
        projectService.save(project);
        return project.getDetails().getInvestorEmailAddresses();
    }

    public Set<EmailAddress> removeInvestor(CurrentUser currentUser, String projectId, String email) {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        User investor = findInvestors(new EmailAddress(email));

        investor.removeProject(project.getId());
        userService.update(investor);

        project.getDetails().removeInvestors(investor);
        projectService.save(project);
        return project.getDetails().getInvestorEmailAddresses();
    }
}
