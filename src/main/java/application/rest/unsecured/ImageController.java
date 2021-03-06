package application.rest.unsecured;

import application.service.ImageService;
import domain.project.Image;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static application.rest.unsecured.ImageController.IMAGES;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(IMAGES)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class ImageController {

    public static final String IMAGES = "/images";

    ImageService imageService;

    @GetMapping("/{imageId}")
    ResponseEntity get(@PathVariable String imageId) {
        Image image = imageService.load(UUID.fromString(imageId));
        return ResponseEntity
                .status(OK)
                .contentType(MediaType.parseMediaType(image.getMimeType()))
                .body(image.getByteBuffer().array());
    }

}
