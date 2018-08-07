package application.rest;

import application.dto.AddInvestorDto;
import application.dto.CurrentUser;
import application.dto.ProjectDetailsDto;
import application.dto.UpdateProjectDetailsDto;
import application.service.ProjectDetailsService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static application.dto.DtoAssemblers.fromEmailsToDtos;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(
        path = ProjectDetailsController.PROJECT_DETAILS,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class ProjectDetailsController {
    public static final String PROJECT_DETAILS = "/projects/{projectId}/details";

    ProjectDetailsService projectDetailsService;

    @GetMapping
    ResponseEntity<ProjectDetailsDto> get(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable String projectId) {
        return ok(projectDetailsService.get(currentUser.getId(), projectId));
    }

    @PutMapping
    ResponseEntity<ProjectDetailsDto> update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @RequestBody UpdateProjectDetailsDto projectDetailsDto) {
        return ok(
                projectDetailsService.update(currentUser.getId(), projectId, projectDetailsDto)
        );
    }

    @PostMapping("/investors")
    ResponseEntity<List<AddInvestorDto>> addInvesotr(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @RequestBody AddInvestorDto addInvestorDto) {
        return ok(
                fromEmailsToDtos(projectDetailsService.addInvestor(currentUser, projectId, addInvestorDto))
        );
    }

    @DeleteMapping("/investors/{email}")
    ResponseEntity<List<AddInvestorDto>> deleteInvestor(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String email) {
        return ok(
                fromEmailsToDtos(projectDetailsService.removeInvestor(currentUser, projectId, email))
        );
    }

}
