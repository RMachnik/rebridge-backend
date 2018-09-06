package application.dto;

import domain.project.Document;
import lombok.Value;

@Value
public class DocumentDto {

    String id;
    String name;

    public static DocumentDto create(Document document) {
        return new DocumentDto(document.getId().toString(), document.getName());
    }
}
