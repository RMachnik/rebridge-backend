package application.service;

import application.dto.CurrentUser;
import domain.DomainExceptions.MissingDocumentation;
import domain.DomainExceptions.UserActionNotAllowed;
import domain.project.Document;
import domain.project.Documentation;
import domain.project.DocumentationRepository;
import domain.project.Image;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import static java.lang.String.format;

@AllArgsConstructor
public class SimpleDocumentationService implements DocumentationService {

    UserService userService;
    ImageService imageService;
    DocumentationRepository documentationRepository;

    @Override
    public Documentation all(CurrentUser user, String projectId) {
        userCanManipulate(user, UUID.fromString(projectId));
        return documentationRepository.findByProject(projectId)
                .orElseThrow(
                        () -> new MissingDocumentation(String.format("There is no documentation for project %s", projectId))
                );
    }

    private void userCanManipulate(CurrentUser user, UUID projectId) {
        if (!userService.findById(user.getId()).canUpdateProject(projectId)) {
            throw new UserActionNotAllowed(format("User %s is not allowed to see documentation for project %s.", user.getId(), projectId));
        }
    }

    @Override
    public Document findDocument(String projectId, String documentId) {
        Documentation documentation = findDocumentationByProjectId(projectId);

        return documentation.getDocuments().stream()
                .filter(document -> document.getId().toString().equals(documentId))
                .findFirst()
                .orElseThrow(
                        () -> new MissingDocumentation(format("Document %s is missing.", documentId))
                );
    }

    private Documentation findDocumentationByProjectId(String projectId) {
        return documentationRepository.findByProject(projectId)
                .orElseThrow(
                        () -> new MissingDocumentation(format("Documentation is missing for projectId %s.", projectId))
                );
    }

    @Override
    public Document uploadDocument(CurrentUser currentUser, String projectId, MultipartFile uploadedFile) throws IOException {
        Documentation documentation = findDocumentationByProjectId(projectId);

        Image image = Image.create(uploadedFile.getName(), uploadedFile.getContentType(), ByteBuffer.wrap(uploadedFile.getBytes()));
        Image saved = imageService.save(projectId, image);

        Document document = Document.create(saved.getName(), saved.getId());

        documentation.addDocument(document);
        documentationRepository.save(documentation);
        return document;
    }

    @Override
    public void delete(CurrentUser currentUser, String projectId, String documentId) {
        Documentation documentation = findDocumentationByProjectId(projectId);
        documentation.delete(UUID.fromString(documentId));
    }
}
