package domain.service

import domain.UserRepository
import io.vavr.control.Try
import spock.lang.Specification

class UserServiceSpec extends Specification {

    def "should throw exception when repo has issue"() {
        given:
        UserRepository mockedRepo = Mock(UserRepository)
        mockedRepo.save(_) >> { Try.failure(new RuntimeException()) }
        UserService userService = new UserService(mockedRepo)

        when:
        userService.create("zdenek", "pass")

        then:
        thrown(DomainExceptions.UserRepositoryException)

    }
}
