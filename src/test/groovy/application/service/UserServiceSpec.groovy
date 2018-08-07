package application.service

import domain.user.User
import domain.user.UserRepository
import spock.lang.Specification

import static domain.user.Roles.ARCHITECT

class UserServiceSpec extends Specification {

    def "should throw exception when repo has issue"() {
        given:
        UserRepository mockedRepo = Mock(UserRepository)
        mockedRepo.findByEmail(_) >> { Optional.of(User.createUser("zdenek@mail.com", "pass", ARCHITECT)) }
        UserService userService = new UserService(mockedRepo, Mock(MailService), Mock(InvitationService))

        when:
        userService.createWithRoleArchitect("zdenek@mail.com", "pass")

        then:
        thrown(ServiceExceptions.ServiceException)
    }
}
