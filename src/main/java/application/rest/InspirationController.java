package application.rest;

import application.dto.DomainMappers;
import application.dto.InspirationDto;
import application.dto.UserDto;
import application.service.InspirationService;
import domain.Inspiration;
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
    public ResponseEntity<List<InspirationDto>> inspirations(
            @AuthenticationPrincipal UserDto userDto,
            @PathVariable String projectId
    ) {
        return ResponseEntity.ok(
                inspirationService.findAll(userDto.getId(), projectId).stream()
                        .map(DomainMappers::fromInspirationToDto)
                        .collect(toList())
        );
    }

    @GetMapping("{inspirationId}")
    public ResponseEntity inspiration(
            @AuthenticationPrincipal UserDto userDto,
            @PathVariable String projectId,
            @PathVariable String inspirationId
    ) {
        return ResponseEntity.ok(inspirationService.findById(userDto.getId(), projectId, inspirationId));
    }

    @PostMapping
    ResponseEntity create(UriComponentsBuilder builder,
                          @AuthenticationPrincipal UserDto userDto,
                          @PathVariable String projectId,
                          @RequestBody InspirationDto inspirationDto
    ) {

        Inspiration createdInspiration = inspirationService.create(userDto.getId(), projectId, inspirationDto.getName());
        UriComponents pathToInspiration = builder.path(INSPIRATIONS)
                .path("{id}")
                .build();

        return ResponseEntity
                .created(pathToInspiration.toUri())
                .body(DomainMappers.fromInspirationToDto(createdInspiration));
    }

    @PutMapping("{inspirationId}")
    ResponseEntity update(
            @AuthenticationPrincipal UserDto userDto,
            @PathVariable String projectId,
            @PathVariable String inspirationId,
            @RequestBody InspirationDto inspirationDto
    ) {
        inspirationDto.setId(inspirationId);
        return ResponseEntity.ok(inspirationService
                .update(userDto.getId(), projectId, inspirationDto));
    }

    @DeleteMapping("{inspirationId}")
    ResponseEntity delete(
            @AuthenticationPrincipal UserDto userDto,
            @PathVariable String projectId,
            @PathVariable String inspirationId
    ) {
        inspirationService.delete(userDto.getId(), projectId, inspirationId);
        return ResponseEntity.noContent().build();
    }

}
