package domain

import application.dto.CreateProjectDto
import domain.project.DomainExceptions
import domain.project.Project
import domain.project.ProjectRepository
import domain.survey.QuestionnaireTemplate
import domain.user.ContactDetails
import domain.user.Roles
import domain.user.User
import spock.lang.Shared
import spock.lang.Specification

class UserSpec extends Specification {

    @Shared
    def existingProjectId = UUID.randomUUID()
    Roles role = Roles.ARCHITECT
    CreateProjectDto createProjectDto = new CreateProjectDto("projectName", UUID.randomUUID().toString())
    QuestionnaireTemplate questionnaireTemplate = QuestionnaireTemplate.empty("some")

    def "user is not populated with Nulls"() {
        given:
        UUID projectId = UUID.randomUUID()
        UUID userId = UUID.randomUUID()
        when:
        User user = new User(userId, "mail@mailinator.com", "password", ContactDetails.empty(), [projectId].toSet(), [Roles.ARCHITECT].toSet())
        then:
        user.id == userId
        user.email == "mail@mailinator.com"
        user.password == "password"
    }

    def "user is created via factoryMethod"() {

        when:
        User user = User.createUser("email@email.com", "password", role)
        then:
        user.id != null
        user.email == "email@email.com"
        user.password == "password"
        user.projectIds.empty
        !user.roles.empty
        user.roles[0] == role
    }

    def "user can check password"() {
        given:
        User user = User.createUser("email@email.com", "password", role)
        expect:
        valid == user.isPasswordValid(password)
        where:
        password   || valid
        "password" || true
        "abc"      || false

    }

    def "user can check project updates capabilities"() {
        given:
        User user = User.createUser("email@email.com", "password", role)
        user.projectIds.add(existingProjectId)
        expect:
        canUpdate == user.canUpdateProject(projectId)
        where:
        projectId         || canUpdate
        existingProjectId || true
        UUID.randomUUID() || false

    }

    def "only architect can create Project"() {
        given:
        User user = User.createUser("email@email.com", "password", role)
        when:
        Project project = user.createProject(createProjectDto, questionnaireTemplate, Mock(ProjectRepository.class))
        then:
        user.projectIds[0] == project.getId()
        project != null
        notThrown(DomainExceptions.UserActionNotAllowed)
    }

    def "regular role can't create Project"() {
        given:
        User user = User.createUser("email@email.com", "password", role)
        user.projectIds.add(existingProjectId)
        user.roles.remove(Roles.ARCHITECT)
        user.roles.add(Roles.INVESTOR)
        when:
        Project project = user.createProject(createProjectDto, questionnaireTemplate, Mock(ProjectRepository.class))
        then:
        project == null
        thrown(DomainExceptions.UserActionNotAllowed)
    }

    def "user can remove project"() {
        given:
        User user = User.createUser("email@email.com", "password", role)
        def project = user.createProject(createProjectDto, questionnaireTemplate, Mock(ProjectRepository))
        when:
        user.removeProject(project.getId())
        then:
        user.projectIds.empty
    }

}
