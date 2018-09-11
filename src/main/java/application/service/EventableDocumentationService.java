package application.service;

import application.dto.CurrentUser;
import domain.event.ChangeEvent;
import domain.project.Document;
import domain.project.Documentation;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
public class EventableDocumentationService implements DocumentationService {

    DocumentationService documentationService;
    ChangeEventService changeEventService;

    @Override
    public Documentation all(CurrentUser user, String projectId) {
        return documentationService.all(user, projectId);
    }

    @Override
    public Document findDocument(String documentationId, String documentId) {
        return documentationService.findDocument(documentationId, documentId);
    }

    @Override
    public Document uploadDocument(CurrentUser currentUser, String projectId, MultipartFile uploadedFile) throws IOException {
        Document document = documentationService.uploadDocument(currentUser, projectId, uploadedFile);
        changeEventService.publish(ChangeEvent.create(
                currentUser.getUUID(),
                UUID.fromString(projectId),
                String.format("New document uploaded to project %s.", projectId))
        );
        return document;
    }

    @Override
    public void delete(CurrentUser currentUser, String projectId, String documentId) {
        documentationService.delete(currentUser, projectId, documentId);
    }
}
