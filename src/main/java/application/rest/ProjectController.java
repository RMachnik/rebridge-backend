package application.rest;

import application.dto.DomainMappers;
import application.dto.ProjectDto;
import application.dto.UserDto;
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
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class ProjectController {

    public static final String PROJECTS = "/projects/";
    ProjectService projectService;

    @GetMapping
    ResponseEntity<List<ProjectDto>> projects(@AuthenticationPrincipal UserDto user) {
        return ResponseEntity.ok(
                projectService.findAllByUserId(user.getId()).stream()
                        .map(DomainMappers::fromProjectToDto)
                        .collect(toList())
        );
    }

    @PostMapping
    ResponseEntity create(
            UriComponentsBuilder builder,
            @AuthenticationPrincipal UserDto user,
            @RequestBody ProjectDto projectDto) {
        Project createdProject = projectService.create(user.getId(), projectDto.getName());

        UriComponents uriComponents =
                builder.
                        path(PROJECTS)
                        .path("{id}")
                        .buildAndExpand(createdProject.getId());

        return ResponseEntity
                .created(uriComponents.toUri())
                .body(DomainMappers.fromProjectToDto(createdProject));
    }

    @GetMapping("{projectId}")
    ResponseEntity project(@AuthenticationPrincipal UserDto userDto, @PathVariable String projectId) {
        return ResponseEntity.ok(projectService.findByUserIdAndProjectId(userDto.getId(), projectId));
    }

    @PutMapping("{projectId}")
    ResponseEntity update(
            @AuthenticationPrincipal UserDto userDto,
            @PathVariable String projectId,
            @RequestBody ProjectDto projectDto) {
        projectDto.setId(projectId);
        Project updated = projectService.update(userDto.getId(), projectDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{projectId}")
    ResponseEntity delete(@AuthenticationPrincipal UserDto userDto, @PathVariable String projectId) {
        projectService.remove(userDto.getId(), projectId);
        return ResponseEntity.noContent().build();
    }
}
