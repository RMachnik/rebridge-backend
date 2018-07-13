package application.rest;

import application.dto.CurrentUser;
import application.dto.DtoAssemblers;
import application.dto.ProjectDto;
import application.service.ProjectService;
import domain.Project;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = ProjectController.PROJECTS,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class ProjectController {

    public static final String PROJECTS = "/projects";
    ProjectService projectService;

    @GetMapping
    ResponseEntity<List<ProjectDto>> projects(@AuthenticationPrincipal CurrentUser user) {
        return ResponseEntity.ok(
                projectService.findAllByUserId(user.getId()).stream()
                        .map(DtoAssemblers::fromProjectToDto)
                        .collect(toList())
        );
    }

    @PostMapping
    ResponseEntity create(
            UriComponentsBuilder builder,
            @AuthenticationPrincipal CurrentUser user,
            @RequestBody ProjectDto projectDto) {
        Project createdProject = projectService.create(user.getId(), projectDto.getName());

        UriComponents uriComponents =
                builder.
                        path(PROJECTS)
                        .path("{id}")
                        .buildAndExpand(createdProject.getId());

        return ResponseEntity
                .created(uriComponents.toUri())
                .body(DtoAssemblers.fromProjectToDto(createdProject));
    }

    @GetMapping("/{projectId}")
    ResponseEntity project(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable String projectId) {
        return ResponseEntity.ok(projectService.findByUserIdAndProjectId(currentUser.getId(), projectId));
    }

    @PutMapping("/{projectId}")
    ResponseEntity update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @RequestBody ProjectDto projectDto) {
        projectDto.setId(projectId);
        Project updated = projectService.update(currentUser.getId(), projectDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{projectId}")
    ResponseEntity delete(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable String projectId) {
        projectService.remove(currentUser.getId(), projectId);
        return ResponseEntity.noContent().build();
    }
}
