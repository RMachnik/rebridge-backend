package application.dto

import application.rest.DocumentationController
import domain.project.Document
import domain.project.Documentation
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

class DocumentationDtoSpec extends Specification {

    def "should create different urls"() {
        given:

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(DocumentationController.DOCUMENTATION).path("/{id}")
        Documentation documentation = Documentation.empty(UUID.randomUUID())
        documentation.addDocument(Document.create(new MockMultipartFile("cos", "cos".getBytes())))
        documentation.addDocument(Document.create(new MockMultipartFile("cos1", "cos1".getBytes())))

        when:
        DocumentationDto documentationDto = DocumentationDto.convert(documentation, builder)

        then:
        documentationDto.documents.size() == 2
        documentationDto.documents[0].url != documentationDto.documents[1].url
    }
}
