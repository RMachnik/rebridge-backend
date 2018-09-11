package application.service;

import application.dto.CurrentUser;
import domain.project.Document;
import domain.project.Documentation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentationService {

    Documentation all(CurrentUser user, String projectId);

    Document findDocument(String documentationId, String documentId);

    Document uploadDocument(CurrentUser currentUser, String projectId, MultipartFile uploadedFile) throws IOException;

    void delete(CurrentUser currentUser, String projectId, String documentId);
}
