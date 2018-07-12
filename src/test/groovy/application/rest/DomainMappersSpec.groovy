package application.rest

import application.dto.DomainMappers
import application.dto.ProjectDto
import domain.Project
import spock.lang.Specification

class DomainMappersSpec extends Specification {

    def "should convert to dto"() {
        given:
        Project newProject = Project.create("new")

        when:
        ProjectDto projectToDto = DomainMappers.fromProjectToDto(newProject)

        then:
        !projectToDto.id.empty
        projectToDto.name == "new"
        projectToDto.inspirationIds.empty
    }
}
