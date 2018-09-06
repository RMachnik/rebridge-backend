package application.service;

import application.dto.CurrentUser;
import domain.DomainExceptions;
import domain.project.Document;
import domain.project.Documentation;
import domain.project.DocumentationRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static java.lang.String.format;

@AllArgsConstructor
public class SimpleDocumentationService implements DocumentationService {

    UserService userService;
    DocumentationRepository documentationRepository;

    @Override
    public Documentation all(CurrentUser user, String projectId) {
        UUID projectUUID = UUID.fromString(projectId);

        userCanManipulate(user, projectUUID);
        return documentationRepository.findByProject(projectUUID);
    }

    private void userCanManipulate(CurrentUser user, UUID projectId) {
        if (!userService.findById(user.getId()).canUpdateProject(projectId)) {
            throw new DomainExceptions.UserActionNotAllowed(format("User %s is not allowed to see documentation for project %s.", user.getId(), projectId));
        }
    }

    @Override
    public Document findDocument(String documentationId, String documentId) {
        Documentation documentation = findDocumentation(documentationId);

        return documentation.getDocuments().stream()
                .filter(document -> document.getId().toString().equals(documentId))
                .findFirst()
                .orElseThrow(
                        () -> new DomainExceptions.MissingDocumentation(format("Document %s is missing.", documentId))
                );
    }

    private Documentation findDocumentation(String documentationId) {
        return documentationRepository.findById(UUID.fromString(documentationId))
                .orElseThrow(
                        () -> new DomainExceptions.MissingDocumentation(format("Documentation %s is missing.", documentationId))
                );
    }

    @Override
    public Document uploadDocument(CurrentUser currentUser, String projectId, String documentationId, MultipartFile uploadedFile) throws IOException {
        Documentation documentation = findDocumentation(documentationId);
        Document document = Document.create(uploadedFile);
        documentation.addDocument(document);
        documentationRepository.save(documentation);
        return document;
    }
}
