package application.service;

import application.dto.*;
import domain.project.Details;
import domain.user.EmailAddress;

import java.util.List;
import java.util.Set;

public interface ProjectDetailsService {

    ProjectDetailsDto get(String userId, String projectId);

    List<InvestorDto> findInvestors(Details details);

    Details create(String userId, String projectId, UpdateProjectDetailsDto updateProjectDetailsDto);

    ProjectDetailsDto update(String userId, String projectId, UpdateProjectDetailsDto updateProjectDetailsDto);

    Set<EmailAddress> addInvestor(CurrentUser currentUser, String projectId, AddInvestorDto addInvestorDto);

    Set<EmailAddress> removeInvestor(CurrentUser currentUser, String projectId, String email);
}
