package application.service;

import application.service.ServiceExceptions.ServiceException;
import domain.project.Inspiration;
import domain.project.Picture;
import domain.project.PictureRepository;
import domain.project.Project;
import lombok.AllArgsConstructor;

import java.nio.ByteBuffer;
import java.util.UUID;

import static java.lang.String.format;

@AllArgsConstructor
public class PictureService {

    ProjectService projectService;
    PictureRepository pictureRepository;

    public Picture store(String userId, UUID projectId, UUID inspirationId, ByteBuffer content) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId.toString());
        Inspiration inspiration = project.findInspiration(inspirationId);

        Picture picture = pictureRepository.save(new Picture(UUID.randomUUID(), content))
                .getOrElseThrow(() -> new ServiceException(format("unable to store picture for inspiration %s", inspiration)));

        inspiration.getDetails().updatePictureId(picture.getId());

        projectService.save(project);
        return picture;
    }

    public ByteBuffer load(UUID pictureId) {
        return pictureRepository.findById(pictureId)
                .map(Picture::getByteBuffer)
                .orElseThrow(() -> new ServiceException(format("unable to find picture %s", pictureId)));
    }
}
