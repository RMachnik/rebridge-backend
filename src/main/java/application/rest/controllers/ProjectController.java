package application.rest.controllers;

import application.rest.controllers.dto.ProjectDto;
import application.rest.controllers.dto.UserDto;
import domain.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = "/projects/",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class ProjectController {

    ProjectService projectService;

    @GetMapping
    ResponseEntity<List<ProjectDto>> projects(@AuthenticationPrincipal UserDto user) {
        return ResponseEntity.ok(
                projectService.findByUserId(user.getId()).stream()
                        .map(DomainMappers::fromProjectToDto)
                        .collect(toList())
        );
    }

    @GetMapping("{projectId}")
    ResponseEntity project(@AuthenticationPrincipal UserDto userDto, @PathVariable String projectId) {
        return ResponseEntity.ok(projectService.findByUserIdAndProjectId(userDto.getId(), projectId));
    }

    @PostMapping
    ResponseEntity create(@AuthenticationPrincipal UserDto user, String projectName) {
        return ResponseEntity.created(
                URI.create(
                        projectService.create(user.getId(), projectName)
                )
        )
                .build();
    }

    @PutMapping("{projectId}")
    ResponseEntity update(@PathVariable String projectId, ProjectDto projectDto) {
        projectDto.setId(projectId);
        return projectService
                .getProjectRepository()
                .save(DomainMappers.fromDtoToProject(projectDto))
                .map(project -> ResponseEntity.ok(project))
                .getOrElseGet(ex -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("{projectId}")
    ResponseEntity delete(@AuthenticationPrincipal UserDto userDto, @PathVariable String projectId) {
        projectService.remove(userDto.getId(), projectId);
        return ResponseEntity.noContent().build();
    }
}
