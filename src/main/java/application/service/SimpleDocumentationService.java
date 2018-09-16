package application.service;

import application.dto.CurrentUser;
import domain.project.Document;
import domain.project.Documentation;
import domain.project.Image;
import domain.project.Project;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

@AllArgsConstructor
public class SimpleDocumentationService implements DocumentationService {

    UserService userService;
    ImageService imageService;
    ProjectService projectService;

    @Override
    public Documentation all(CurrentUser user, String projectId) {
        return projectService.findByUserIdAndProjectId(user.getId(), projectId).getDocumentation();
    }

    @Override
    public Document uploadDocument(CurrentUser currentUser, String projectId, MultipartFile uploadedFile) throws IOException {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        Documentation documentation = project.getDocumentation();

        Image image = Image.create(uploadedFile.getName(), uploadedFile.getContentType(), ByteBuffer.wrap(uploadedFile.getBytes()));
        Image saved = imageService.save(projectId, image);

        Document document = Document.create(saved.getName(), saved.getId());

        documentation.addDocument(document);
        projectService.save(project);
        return document;
    }

    @Override
    public void delete(CurrentUser currentUser, String projectId, String documentId) {
        Documentation documentation = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId).getDocumentation();
        documentation.delete(UUID.fromString(documentId));
    }
}
