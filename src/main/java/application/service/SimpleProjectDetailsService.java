package application.service;

import application.dto.*;
import domain.project.Details;
import domain.project.Project;
import domain.survey.QuestionnaireTemplate;
import domain.user.EmailAddress;
import domain.user.User;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class SimpleProjectDetailsService implements ProjectDetailsService {

    UserService userService;
    ProjectService projectService;
    QuestionnaireTemplateService questionnaireTemplateService;

    @Override
    public ProjectDetailsDto get(String userId, String projectId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Details details = project.getDetails();

        List<InvestorDto> investors =
                findInvestors(details);

        ProjectDetailsDto projectDetailsDto = ProjectDetailsDto.create(project, investors);
        return projectDetailsDto;
    }

    @Override
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
            return InvestorDto.create(possibleUser.get());
        } else {
            return InvestorDto.builder()
                    .email(email)
                    .build();
        }
    }


    @Override
    public Details create(String userId, String projectId, UpdateProjectDetailsDto updateProjectDetailsDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Details details = project.createDetails(updateProjectDetailsDto);
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

    @Override
    public ProjectDetailsDto update(String userId, String projectId, UpdateProjectDetailsDto updateProjectDetailsDto) {
        QuestionnaireTemplate questionnaireTemplate =
                ofNullable(updateProjectDetailsDto.getQuestionnaireId())
                        .map(questionnaireTemplateService::findById)
                        .map(Optional::get)
                        .orElse(questionnaireTemplateService.findAll().get(0));

        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        project.updateDetails(updateProjectDetailsDto, questionnaireTemplate);
        projectService.save(project);

        return ProjectDetailsDto.create(project, findInvestors(project.getDetails()));
    }

    @Override
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

    @Override
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
