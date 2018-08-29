package application.rest


import application.dto.ProjectDto
import domain.project.Project
import domain.survey.QuestionnaireTemplate
import spock.lang.Specification

class DtoAssemblersSpec extends Specification {

    def "should convert to dto"() {
        QuestionnaireTemplate questionnaireTemplate = QuestionnaireTemplate.empty("some")
        given:
        Project newProject = Project.create("new", questionnaireTemplate)

        when:
        ProjectDto projectToDto = ProjectDto.create(newProject)

        then:
        !projectToDto.id.empty
        projectToDto.name == "new"
        projectToDto.questionnaireTemplateId == questionnaireTemplate.getId().toString()
    }
}
