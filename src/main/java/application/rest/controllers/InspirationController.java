package application.rest.controllers;

import application.rest.model.InspirationMapper;
import domain.Inspiration;
import domain.InspirationRepository;
import domain.Project;
import domain.ProjectRepository;
import dto.InspirationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/projects/{projectId}/inspirations/")
public class InspirationController {

    ProjectRepository projectRepository;
    InspirationRepository inspirationRepository;

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
        return inspirationRepository.add(projectId,
                Inspiration
                        .builder()
                        .name(name)
                        .build())
                .map(URI::create)
                .map(ResponseEntity::created)
                .orElse(ResponseEntity.badRequest())
                .build();
    }

    @PutMapping("{inspirationId}")
    ResponseEntity update(@PathVariable String inspirationId, InspirationDto inspirationDto) {
        return ResponseEntity.ok(
                inspirationRepository.update(
                        Inspiration.builder()
                                .id(inspirationId)
                                .name(inspirationDto.getName())
                                .inspirationDetail(InspirationMapper.INSTANCE.fromDtoToInspiration(inspirationDto.getInspirationDetail()))
                                .build()
                )
        );
    }

}
