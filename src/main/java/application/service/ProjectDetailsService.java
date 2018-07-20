package application.service;

import application.dto.CreateUpdateProjectDetailsDto;
import application.dto.DtoAssemblers;
import application.dto.InvestorDto;
import application.dto.ProjectDetailsDto;
import domain.project.Details;
import domain.project.Project;
import domain.user.EmailAddress;
import domain.user.User;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static application.dto.DtoAssemblers.fromInformationToDto;
import static java.util.stream.Collectors.toList;

@Value
public class ProjectDetailsService {

    UserService userService;
    ProjectService projectService;

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

        List<User> investors = findInvestors(projectDetailsDto.getInvestorEmails());
        investors.stream()
                .forEach(user -> {
                            user.addProject(project.getId());
                            userService.update(user);
                        }
                );

        project.createDetails(projectDetailsDto);
        projectService.save(project);

        return projectDetailsDto;
    }

    private List<User> findInvestors(List<String> investors) {
        return investors.stream()
                .map(userService::tryFindUserByEmail)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    public CreateUpdateProjectDetailsDto update(String userId, String projectId, CreateUpdateProjectDetailsDto updateProjectDetailsDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);

        Details details = project.getDetails();
        Details updated = details.update(updateProjectDetailsDto);

        project.setDetails(updated);

        projectService.save(project);
        return updateProjectDetailsDto;
    }
}
