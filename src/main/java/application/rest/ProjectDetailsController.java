package application.rest;

import application.dto.*;
import application.service.ProjectDetailsService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

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
        return ResponseEntity
                .ok(projectDetailsService.get(currentUser.getId(), projectId));
    }

    @PostMapping
    ResponseEntity<CreateUpdateProjectDetailsDto> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @RequestBody CreateUpdateProjectDetailsDto projectDetailsDto) {
        return ResponseEntity
                .ok(projectDetailsService.create(currentUser.getId(), projectId, projectDetailsDto));

    }

    @PutMapping
    ResponseEntity<CreateUpdateProjectDetailsDto> update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @RequestBody CreateUpdateProjectDetailsDto projectDetailsDto) {
        return ResponseEntity.ok(
                projectDetailsService.update(currentUser.getId(), projectId, projectDetailsDto)
        );
    }

    @PutMapping("/investors")
    ResponseEntity<AddInvestorDto> addInvesotr(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @RequestBody AddInvestorDto addInvestorDto) {
        return ResponseEntity.ok(
                DtoAssemblers.fromEmailsToDtos(projectDetailsService.addInvestors(currentUser, projectId, addInvestorDto))
        );
    }

    @DeleteMapping("/investors")
    ResponseEntity<AddInvestorDto> deleteInvestor(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @RequestBody AddInvestorDto removeInvestors) {
        return ResponseEntity.ok(
                DtoAssemblers.fromEmailsToDtos(projectDetailsService.removeInvestors(currentUser, projectId, removeInvestors))
        );
    }

}
