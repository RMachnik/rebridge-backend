package application.rest;

import application.dto.CurrentUser;
import application.dto.DocumentationDto;
import application.service.DocumentationService;
import domain.project.Document;
import domain.project.Documentation;
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

import static application.rest.unsecured.ImageController.IMAGES;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = DocumentationController.DOCUMENTATION,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class DocumentationController {

    public static final String DOCUMENTATION = "/projects/{projectId}/documentation";
    DocumentationService documentationService;

    @GetMapping
    ResponseEntity<DocumentationDto> all(@AuthenticationPrincipal CurrentUser user, @PathVariable String projectId, UriComponentsBuilder uriComponentsBuilder) {
        Documentation documentation = documentationService.all(user, projectId);
        UriComponentsBuilder path = uriComponentsBuilder
                .path(IMAGES)
                .path("/{id}");
        return ResponseEntity.ok(DocumentationDto.convert(documentation, path));
    }

    @PostMapping(
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity upload(
            UriComponentsBuilder builder,
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @RequestParam("uploadedFile") MultipartFile uploadedFile
    ) throws IOException {
        Document document = documentationService.uploadDocument(currentUser, projectId, uploadedFile);
        UriComponents pathToDocument = builder
                .path(IMAGES)
                .path("{id}")
                .buildAndExpand(document.getId());

        return ResponseEntity
                .created(pathToDocument.toUri())
                .body(document.getId());
    }

    @DeleteMapping("/{documentId}")
    ResponseEntity delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String documentId
    ) {
        documentationService.delete(currentUser, projectId, documentId);
        return ResponseEntity.ok("");
    }

}
