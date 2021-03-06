package application.rest;

import application.dto.CreateOrUpdateInspirationDto;
import application.dto.CurrentUser;
import application.dto.InspirationDto;
import application.service.ImageService;
import application.service.InspirationService;
import domain.project.Image;
import domain.project.Inspiration;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static application.rest.unsecured.ImageController.IMAGES;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(
        path = InspirationController.INSPIRATIONS,
        produces = APPLICATION_JSON_UTF8_VALUE,
        consumes = APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class InspirationController {

    public static final String INSPIRATIONS = "/projects/{projectId}/inspirations";

    InspirationService inspirationService;
    ImageService imageService;

    @GetMapping
    public ResponseEntity<List<InspirationDto>> inspirations(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId
    ) {
        return ok(
                inspirationService.findAll(currentUser.getId(), projectId)
                        .stream()
                        .sorted(Collections.reverseOrder(Comparator.comparing(Inspiration::getCreationDate)))
                        .map(InspirationDto::create)
                        .collect(toList())
        );
    }

    @PostMapping(
            consumes = ALL_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity<InspirationDto> create(UriComponentsBuilder builder,
                                          @AuthenticationPrincipal CurrentUser currentUser,
                                          @PathVariable String projectId,
                                          @RequestParam("uploadedFile") MultipartFile uploadedFile
    ) throws IOException {

        String extractedName = uploadedFile.getOriginalFilename();
        Inspiration createdInspiration = inspirationService.create(currentUser.getId(), projectId, CreateOrUpdateInspirationDto.create(extractedName));
        Image savedImage = imageService.addImageToInspiration(
                currentUser.getId(),
                UUID.fromString(projectId),
                UUID.fromString(createdInspiration.getId().toString()),
                uploadedFile);
        Inspiration inspiration = inspirationService.findById(currentUser.getId(), projectId, createdInspiration.getId().toString());
        UriComponents pathToInspiration = builder.path(INSPIRATIONS)
                .path("/{id}")
                .buildAndExpand(projectId, createdInspiration.getId());

        return ResponseEntity
                .created(pathToInspiration.toUri())
                .body(InspirationDto.create(inspiration));
    }

    @GetMapping("/{inspirationId}")
    public ResponseEntity<InspirationDto> inspiration(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String inspirationId
    ) {
        return ok(InspirationDto.create(inspirationService.findById(currentUser.getId(), projectId, inspirationId)));
    }

    @PutMapping("/{inspirationId}")
    ResponseEntity<InspirationDto> update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String inspirationId,
            @RequestBody CreateOrUpdateInspirationDto inspirationDto
    ) {
        return ok(
                InspirationDto.create(inspirationService
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

        Image savedImage = imageService.addImageToInspiration(
                currentUser.getId(),
                UUID.fromString(projectId),
                UUID.fromString(inspirationId),
                uploadedFile);

        UriComponents pathToPicture = builder
                .path(IMAGES)
                .path("{}")
                .buildAndExpand(savedImage.getId());

        return ResponseEntity
                .created(pathToPicture.toUri())
                .body(savedImage.getId());
    }
}
