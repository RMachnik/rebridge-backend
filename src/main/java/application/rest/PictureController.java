package application.rest;

import application.dto.CurrentUser;
import application.service.PictureService;
import domain.project.Picture;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import static application.rest.PictureController.PICTURES;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(PICTURES)
@MultipartConfig(fileSizeThreshold = 20971520)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class PictureController {

    public static final String PICTURES = "/projects/{projectId}/inspirations/{inspirationId}/pictures/";
    PictureService pictureService;

    @GetMapping(path = "{pictureId}", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity get(@PathVariable String pictureId) {
        ByteBuffer buffer = pictureService.load(UUID.fromString(pictureId));
        return ResponseEntity.ok(buffer.array());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity upload(
            UriComponentsBuilder builder,
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String inspirationId,
            @RequestParam("uploadedFile") MultipartFile uploadedFile) throws IOException {
        ByteBuffer picture = ByteBuffer.wrap(uploadedFile.getBytes());
        Picture savedPicture = pictureService.store(currentUser.getId(), UUID.fromString(projectId), UUID.fromString(inspirationId), picture);

        UriComponents pathToPicture = builder
                .path(PICTURES)
                .path("{id}")
                .buildAndExpand(projectId, inspirationId, savedPicture.getId());

        return ResponseEntity
                .created(pathToPicture.toUri())
                .body(savedPicture.getId());
    }
}
