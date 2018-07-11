package application.rest.controllers;

import application.rest.controllers.dto.InspirationDto;
import domain.Inspiration;
import domain.service.InspirationService;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = InspirationController.INSPIRATIONS,
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class InspirationController {

    public static final String INSPIRATIONS = "/projects/{projectId}/inspirations/";
    InspirationService inspirationService;

    @GetMapping
    public ResponseEntity<List<InspirationDto>> inspirations(@PathVariable String projectId) {
        return ResponseEntity.ok(
                inspirationService.findInspirations(projectId).stream()
                        .map(DomainMappers::fromInspirationToDto)
                        .collect(toList())
        );
    }

    @GetMapping("{inspirationId}")
    public ResponseEntity inspiration(@PathVariable String inspirationId) {
        return Try.of(
                () -> inspirationService.getInspirationById(inspirationId)
        )
                .toEither()
                .map(ResponseEntity::ok)
                .getOrElseGet(ex -> ResponseEntity.badRequest().build());
    }

    @PostMapping
    ResponseEntity create(UriComponentsBuilder builder,
                          @PathVariable String projectId,
                          @RequestBody InspirationDto inspirationDto
    ) {

        Inspiration createdInspiration = inspirationService.create(projectId, inspirationDto.getName());
        UriComponents pathToInspiration = builder.path(INSPIRATIONS)
                .path("{id}")
                .build();

        return ResponseEntity
                .created(pathToInspiration.toUri())
                .body(DomainMappers.fromInspirationToDto(createdInspiration));
    }

    @PutMapping("{inspirationId}")
    ResponseEntity update(@PathVariable String inspirationId, @RequestBody InspirationDto inspirationDto) {
        return inspirationService
                .getInspirationRepository()
                .save(DomainMappers.fromDtoToInspiration(inspirationId, inspirationDto))
                .mapTry(ResponseEntity::ok)
                .getOrElseGet(
                        ex -> ResponseEntity
                                .badRequest()
                                .build()
                );
    }

    @DeleteMapping("{inspirationId}")
    ResponseEntity delete(@PathVariable String projectId, @PathVariable String inspirationId) {
        inspirationService.delete(projectId, inspirationId);
        return ResponseEntity.noContent().build();
    }

}
