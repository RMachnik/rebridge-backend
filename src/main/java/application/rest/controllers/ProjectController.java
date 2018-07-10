package application.rest.controllers;

import domain.Project;
import domain.ProjectRepository;
import domain.User;
import domain.UserRepository;
import dto.ProjectDto;
import dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/projects/")
public class ProjectController {

    UserRepository userRepository;
    ProjectRepository projectRepository;

    @GetMapping
    ResponseEntity<List<ProjectDto>> projects(@AuthenticationPrincipal UserDto user) {
        return ResponseEntity.ok(
                getBody(user)
        );
    }

    private List<ProjectDto> getBody(@AuthenticationPrincipal UserDto user) {
        return userRepository.findByUsername(user.getUsername())
                .map(User::getProjects)
                .map(List::stream)
                .map(
                        stream -> stream.map(project ->
                                ProjectDto
                                        .builder()
                                        .id(project.getId())
                                        .name(project.getName())
                                        .build())
                                .collect(toList())
                )
                .orElse(Collections.EMPTY_LIST);
    }

    @PostMapping
    ResponseEntity add(@AuthenticationPrincipal UserDto user, String projectName) {

        //todo extract to service
        User existingUser = userRepository.findByUsername(user.getUsername()).get();

        Optional<Project> newProject = projectRepository.add(
                Project.builder()
                        .name(projectName)
                        .build());

        existingUser.getProjects().add(newProject.get());

        return newProject
                .map(Project::getId)
                .map(URI::create)
                .map(ResponseEntity::created)
                .orElse(ResponseEntity.badRequest())
                .build();

    }

    @PutMapping("{projectId}")
    ResponseEntity update(@PathVariable String projectId, ProjectDto projectDto) {
        return projectRepository.update(projectId,
                Project.builder()
                        .name(projectDto.getName())
                        .build())
                .map(project -> ResponseEntity.ok(project))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("{projectId}")
    ResponseEntity<String> delete(@PathVariable String projectId) {
        //todo make sure that all links are deleted
        // this action can be performed only by project owner - this needs to be figured out
        return ResponseEntity.ok(
                projectRepository.delete(projectId)
        );
    }
}
