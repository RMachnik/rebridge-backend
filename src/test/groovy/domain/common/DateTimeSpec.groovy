package domain.common

import spock.lang.Specification

class DateTimeSpec extends Specification {

    def "should return simple date"() {
        given:
        String someDate = "2018-08-30T10:54:33.731"
        when:
        DateTime dateTime = new DateTime(someDate);
        then:
        dateTime.simpleDate() == "2018-08-30 10:54"
    }
}
