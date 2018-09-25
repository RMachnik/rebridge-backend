package application.dto

import spock.lang.Specification

class CreateProjectDtoTest extends Specification {

    def "simple constructor check"() {
        when:
        new CreateProjectDto(null)
        then:
        thrown(NullPointerException)
    }
}
