package application.dto;

import domain.project.Documentation;
import lombok.Value;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class DocumentationDto {

    String id;
    List<DocumentDto> documents;

    public static DocumentationDto convert(Documentation documentation, UriComponentsBuilder uriComponentsBuilder) {
        List<DocumentDto> documents = documentation.getDocuments().stream()
                .map(document -> DocumentDto
                        .create(
                                document,
                                uriComponentsBuilder
                                        .buildAndExpand(document.getContentId()).toUriString())
                )
                .collect(Collectors.toList());

        return new DocumentationDto(documentation.getId().toString(), documents);
    }
}
