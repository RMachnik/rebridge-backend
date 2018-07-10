package application.rest.controllers;

import application.rest.model.InspirationMapper;
import domain.Inspiration;
import domain.InspirationDetail;
import domain.Project;
import domain.ProjectRepository;
import dto.InspirationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/projects/{projectId}/inspirations/")
public class InspirationController {

    ProjectRepository projectRepository;

    @GetMapping
    public ResponseEntity<List<InspirationDto>> inspirations(@PathVariable String projectId) {
        return ResponseEntity.ok(
                projectRepository
                        .findById(projectId)
                        .map(Project::getInspirations)
                        .map(List::stream)
                        .map(stream ->
                                stream.map(single ->
                                        InspirationDto
                                                .builder()
                                                .name(single.getName())
                                                .build())
                                        .collect(toList()))
                        .orElse(Collections.EMPTY_LIST)
        );
    }

    @PostMapping
    ResponseEntity add(@PathVariable String projectId, String name) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    String uuid = UUID.randomUUID().toString();
                    project.getInspirations().add(Inspiration
                            .builder()
                            .id(uuid)
                            .name(name)
                            .build());
                    return uuid;
                })
                .map(URI::create)
                .map(ResponseEntity::created)
                .orElse(ResponseEntity.badRequest())
                .build();

    }

    @PutMapping("{inspirationId}")
    ResponseEntity update(@PathVariable String projectId, @PathVariable String inspirationId, InspirationDto inspirationDto) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    Inspiration foundInspiration = project.getInspirations().stream()
                            .filter(inspiration -> inspiration.getId().equals(inspirationId))
                            .findFirst()
                            .get();
                    InspirationDetail inspirationDetail = InspirationMapper.INSTANCE.fromDtoToInspiration(inspirationDto.getInspirationDetail());
                    Inspiration updatedInspiration = Inspiration.builder()
                            .id(foundInspiration.getId())
                            .name(foundInspiration.getName())
                            .inspirationDetail(inspirationDetail)
                            .build();
                    project.getInspirations().add(updatedInspiration);
                    return updatedInspiration;
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .badRequest()
                        .build());
    }

}
