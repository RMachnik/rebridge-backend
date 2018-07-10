package application.rest.model

import application.rest.controllers.DomainMappers
import application.rest.controllers.dto.ProjectDto
import domain.Project
import spock.lang.Specification

class DomainMappersSpec extends Specification {

    def "check basic mappers"() {
        given:
        ProjectDto projectDto = ProjectDto
                .builder()
                .id("id")
                .name("name")
                .build()

        when:
        Project project = DomainMappers.fromDtoToProject(projectDto)

        then:
        project.id == projectDto.id
        project.name == projectDto.name
    }
}
