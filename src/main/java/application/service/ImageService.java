package application.service;

import application.service.ServiceExceptions.ServiceException;
import domain.project.Image;
import domain.project.ImageRepository;
import domain.project.Inspiration;
import domain.project.Project;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import static java.lang.String.format;

@AllArgsConstructor
public class ImageService {

    ProjectService projectService;
    ImageRepository imageRepository;

    public Image load(UUID pictureId) {
        return imageRepository.findById(pictureId)
                .orElseThrow(() -> new ServiceException(format("unable to find picture %s", pictureId)));
    }

    public Image addImageToInspiration(String userId, UUID projectId, UUID inspirationId, MultipartFile uploadedImage) throws IOException {
        Image image = Image.create(uploadedImage.getOriginalFilename(), uploadedImage.getContentType(), ByteBuffer.wrap(uploadedImage.getBytes()));

        Project project = projectService.findByUserIdAndProjectId(userId, projectId.toString());
        Inspiration inspiration = project.findInspiration(inspirationId);

        Image saved = imageRepository.save(image)
                .getOrElseThrow(() -> new ServiceException(format("unable save image for inspiration %s", inspirationId)));

        inspiration.updatePictureId(saved.getId());

        projectService.save(project);
        return saved;
    }

    public Image addImageToProject(String userId, String projectId, MultipartFile uploadedImage) throws IOException {
        Image image = Image.create(uploadedImage.getName(), uploadedImage.getContentType(), ByteBuffer.wrap(uploadedImage.getBytes()));
        Image saved = save(projectId, image);

        Project project = projectService.findByUserIdAndProjectId(userId, projectId);

        project.updateImage(saved.getId());

        projectService.save(project);
        return saved;
    }

    public Image save(String projectId, Image image) {
        return imageRepository.save(image)
                .getOrElseThrow(() -> new ServiceException(format("unable to save picture for project %s", projectId)));
    }

    public void delete(UUID imageId) {
        imageRepository.deleteById(imageId);
    }
}
