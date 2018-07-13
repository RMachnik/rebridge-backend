package application.service

import domain.User
import domain.UserRepository
import spock.lang.Specification

class UserServiceSpec extends Specification {

    def "should throw exception when repo has issue"() {
        given:
        UserRepository mockedRepo = Mock(UserRepository)
        mockedRepo.findByUsername(_) >> { Optional.of(User.createUser("zdenek", "pass")) }
        UserService userService = new UserService(mockedRepo)

        when:
        userService.create("zdenek", "pass")

        then:
        thrown(ServiceExceptions.ServiceException)
    }
}
