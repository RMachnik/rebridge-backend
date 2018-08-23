package domain.user

import spock.lang.Specification

class PhoneNumberSpec extends Specification {

    def "should valid phone number"() {
        expect:
        PhoneNumber phoneNumber = null;
        try {
            phoneNumber = PhoneNumber.fromNumber(number)
        }
        catch (RuntimeException ex) {
        }

        if (valid) {
            phoneNumber.value == number
        } else {
            phoneNumber == null
        }

        where:
        number        || valid
        ""            || false
        "123-3"       || false
        "123123123-3" || false
        "123123123"   || true
    }
}
