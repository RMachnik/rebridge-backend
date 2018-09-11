package application.dto


import application.rest.unsecured.ImageController
import domain.project.Document
import domain.project.Documentation
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

class DocumentationDtoSpec extends Specification {

    def "should create different urls"() {
        given:

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ImageController.IMAGES).path("/{id}")
        Documentation documentation = Documentation.empty(UUID.randomUUID())
        documentation.addDocument(Document.create("name", UUID.randomUUID()))
        documentation.addDocument(Document.create("name1", UUID.randomUUID()))

        when:
        DocumentationDto documentationDto = DocumentationDto.convert(documentation, builder)

        then:
        documentationDto.documents.size() == 2
        documentationDto.documents[0].url != documentationDto.documents[1].url
    }
}
