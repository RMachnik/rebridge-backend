package application.dto;

import domain.project.Documentation;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class DocumentationDto {

    String id;
    List<DocumentDto> documents;

    public static DocumentationDto convert(Documentation documentation) {
        List<DocumentDto> documents = documentation.getDocuments().stream()
                .map(DocumentDto::create)
                .collect(Collectors.toList());

        return new DocumentationDto(documentation.getId().toString(), documents);
    }
}
