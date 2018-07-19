package application.service

import domain.user.User
import domain.user.UserRepository
import spock.lang.Specification

class UserServiceSpec extends Specification {

    def "should throw exception when repo has issue"() {
        given:
        UserRepository mockedRepo = Mock(UserRepository)
        mockedRepo.findByEmail(_) >> { Optional.of(User.createUser("zdenek@mail.com", "pass")) }
        UserService userService = new UserService(mockedRepo)

        when:
        userService.create("zdenek@mail.com", "pass")

        then:
        thrown(ServiceExceptions.ServiceException)
    }
}
