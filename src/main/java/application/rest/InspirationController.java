package application.rest;

import application.dto.CreateOrUpdateInspirationDto;
import application.dto.CurrentUser;
import application.dto.DtoAssemblers;
import application.dto.InspirationDto;
import application.service.InspirationService;
import domain.project.Inspiration;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static application.dto.DtoAssemblers.fromInspirationToDto;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(
        path = InspirationController.INSPIRATIONS,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class InspirationController {

    public static final String INSPIRATIONS = "/projects/{projectId}/inspirations";
    InspirationService inspirationService;

    @GetMapping
    public ResponseEntity<List<InspirationDto>> inspirations(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId
    ) {
        return ok(
                inspirationService.findAll(currentUser.getId(), projectId).stream()
                        .map(DtoAssemblers::fromInspirationToDto)
                        .collect(toList())
        );
    }

    @PostMapping
    ResponseEntity<InspirationDto> create(UriComponentsBuilder builder,
                                          @AuthenticationPrincipal CurrentUser currentUser,
                                          @PathVariable String projectId,
                                          @RequestBody CreateOrUpdateInspirationDto inspirationDto
    ) {

        Inspiration createdInspiration = inspirationService.create(currentUser.getId(), projectId, inspirationDto);
        UriComponents pathToInspiration = builder.path(INSPIRATIONS)
                .path("{id}")
                .buildAndExpand(projectId, createdInspiration.getId());

        return ResponseEntity
                .created(pathToInspiration.toUri())
                .body(fromInspirationToDto(createdInspiration));
    }

    @GetMapping("/{inspirationId}")
    public ResponseEntity<InspirationDto> inspiration(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String inspirationId
    ) {
        return ok(fromInspirationToDto(inspirationService.findById(currentUser.getId(), projectId, inspirationId)));
    }

    @PutMapping("/{inspirationId}")
    ResponseEntity<InspirationDto> update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String inspirationId,
            @RequestBody CreateOrUpdateInspirationDto inspirationDto
    ) {
        return ok(
                fromInspirationToDto(inspirationService
                        .update(currentUser.getId(), projectId, inspirationId, inspirationDto)));
    }

    @DeleteMapping("/{inspirationId}")
    ResponseEntity delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String inspirationId
    ) {
        inspirationService.delete(currentUser.getId(), projectId, inspirationId);
        return ResponseEntity.noContent().build();
    }

}
