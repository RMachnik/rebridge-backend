package application.service;

import application.dto.*;
import domain.event.ChangeEvent;
import domain.project.Details;
import domain.user.EmailAddress;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.util.UUID.fromString;

@AllArgsConstructor
public class EventableProjectDetailsService implements ProjectDetailsService {

    ProjectDetailsService projectDetailsService;
    ChangeEventService changeEventService;

    @Override
    public ProjectDetailsDto get(String userId, String projectId) {
        return projectDetailsService.get(userId, projectId);
    }

    @Override
    public List<InvestorDto> findInvestors(Details details) {
        return projectDetailsService.findInvestors(details);
    }

    @Override
    public Details create(String userId, String projectId, UpdateProjectDetailsDto updateProjectDetailsDto) {
        return projectDetailsService.create(userId, projectId, updateProjectDetailsDto);
    }

    @Override
    public ProjectDetailsDto update(String userId, String projectId, UpdateProjectDetailsDto updateProjectDetailsDto) {
        ProjectDetailsDto updated = projectDetailsService.update(userId, projectId, updateProjectDetailsDto);
        changeEventService.publish(
                ChangeEvent.create(fromString(userId),
                        fromString(projectId),
                        format("Project %s was updated.", projectId))
        );
        return updated;
    }

    @Override
    public Set<EmailAddress> addInvestor(CurrentUser currentUser, String projectId, AddInvestorDto addInvestorDto) {
        return projectDetailsService.addInvestor(currentUser, projectId, addInvestorDto);
    }

    @Override
    public Set<EmailAddress> removeInvestor(CurrentUser currentUser, String projectId, String email) {
        return projectDetailsService.removeInvestor(currentUser, projectId, email);
    }
}
