package application.rest

import application.dto.ProjectDto
import domain.project.Project
import spock.lang.Specification

class DtoAssemblersSpec extends Specification {

    def "should convert to dto"() {
        given:
        Project newProject = Project.create("new")

        when:
        ProjectDto projectToDto = ProjectDto.create(newProject)

        then:
        !projectToDto.id.empty
        projectToDto.name == "new"
    }
}
