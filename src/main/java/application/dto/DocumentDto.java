package application.dto;

import domain.project.Document;
import lombok.Value;

@Value
public class DocumentDto {

    String id;
    String name;
    String url;

    public static DocumentDto create(Document document, String url) {
        return new DocumentDto(
                document.getId().toString(),
                document.getName(),
                url
        );
    }
}
