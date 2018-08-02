package application.rest

import application.dto.DtoAssemblers
import application.dto.ProjectDto
import domain.project.Project
import spock.lang.Specification

class DtoAssemblersSpec extends Specification {

    def "should convert to dto"() {
        def questionnaireTemplateId = UUID.randomUUID()
        given:
        Project newProject = Project.create("new", questionnaireTemplateId)

        when:
        ProjectDto projectToDto = DtoAssemblers.fromProjectToDto(newProject)

        then:
        !projectToDto.id.empty
        projectToDto.name == "new"
        projectToDto.inspirationIds.empty
        projectToDto.questionnaireTemplateId == questionnaireTemplateId.toString()
    }
}
