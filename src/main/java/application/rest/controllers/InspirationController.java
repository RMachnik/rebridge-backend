package application.rest.controllers;

import application.rest.controllers.dto.InspirationDto;
import domain.service.InspirationService;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = "/projects/{projectId}/inspirations/",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class InspirationController {

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
    ResponseEntity create(@PathVariable String projectId, String name) {
        return ResponseEntity
                .created(
                        URI.create(inspirationService.create(projectId, name))
                )
                .build();

    }

    @PutMapping("{inspirationId}")
    ResponseEntity update(@PathVariable String inspirationId, InspirationDto inspirationDto) {
        inspirationDto.setId(inspirationId);
        return inspirationService
                .getInspirationRepository()
                .save(DomainMappers.fromDtoToInspiration(inspirationDto))
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
