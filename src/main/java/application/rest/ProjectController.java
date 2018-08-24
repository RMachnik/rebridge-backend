package application.rest;

import application.dto.CreateProjectDto;
import application.dto.CurrentUser;
import application.dto.DtoAssemblers;
import application.dto.ProjectDto;
import application.service.ImageService;
import application.service.ProjectService;
import domain.project.Image;
import domain.project.Project;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

import static application.dto.DtoAssemblers.fromProjectToDto;
import static application.rest.ImageController.IMAGES;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

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
    ImageService imageService;

    @GetMapping
    ResponseEntity<List<ProjectDto>> projects(@AuthenticationPrincipal CurrentUser user) {
        return ResponseEntity.ok(
                projectService.findAllByUserId(user.getId()).stream()
                        .map(DtoAssemblers::fromProjectToDto)
                        .collect(toList())
        );
    }

    @PostMapping
    ResponseEntity<ProjectDto> create(
            UriComponentsBuilder builder,
            @AuthenticationPrincipal CurrentUser user,
            @RequestBody CreateProjectDto createProjectDto) {

        Project createdProject = projectService.create(user.getId(), createProjectDto);

        UriComponents uriComponents =
                builder.
                        path(PROJECTS)
                        .path("{id}")
                        .buildAndExpand(createdProject.getId());

        return ResponseEntity
                .created(uriComponents.toUri())
                .body(fromProjectToDto(createdProject));
    }

    @GetMapping("/{projectId}")
    ResponseEntity project(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable String projectId) {
        return ResponseEntity.ok(fromProjectToDto(projectService.findByUserIdAndProjectId(currentUser.getId(), projectId)));
    }

    @PutMapping("/{projectId}")
    ResponseEntity update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @RequestBody CreateProjectDto createProjectDto) {
        ProjectDto projectDto = ProjectDto.builder()
                .id(projectId)
                .name(createProjectDto.getName())
                .build();
        Project updated = projectService.update(currentUser.getId(), projectDto);
        return ResponseEntity.ok(fromProjectToDto(updated));
    }

    @DeleteMapping("/{projectId}")
    ResponseEntity delete(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable String projectId) {
        projectService.remove(currentUser.getId(), projectId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            path = "/{inspirationId}/image",
            consumes = ALL_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity uploadInspiration(
            UriComponentsBuilder builder,
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String inspirationId,
            @RequestParam("uploadedFile") MultipartFile uploadedFile) throws IOException {

        Image savedImage = imageService.addImageToProject(
                currentUser.getId(),
                projectId,
                uploadedFile);

        UriComponents pathToPicture = builder
                .path(IMAGES)
                .path("{id}")
                .buildAndExpand(projectId, inspirationId, savedImage.getId());

        return ResponseEntity
                .created(pathToPicture.toUri())
                .body(savedImage.getId());
    }
}
