package domain.common

import domain.DomainExceptions
import domain.DomainExceptions.InvalidPostalCode
import spock.lang.Specification
import spock.lang.Unroll

class AddressSpec extends Specification {

    @Unroll
    def "should validate invalid postal code #postalCode"() {
        expect:
        try {
            new Address("1", "Akacjowa", postalCode, "Miasto")
        } catch (InvalidPostalCode ex) {
            ex == exception
        }

        where:
        postalCode || exception
        "31-2"     || DomainExceptions.InvalidPostalCode
        "-"        || DomainExceptions.InvalidPostalCode
        "333333"   || DomainExceptions.InvalidPostalCode
        "1232-3"   || DomainExceptions.InvalidPostalCode
    }

    @Unroll
    def "should create address with valid code #postalCode"() {
        expect:
        def address = new Address("1", "Akacjowa", postalCode, "Miasto")
        address.postalCode == value

        where:
        postalCode || value
        "30-300"   || "30-300"
        "39-310"   || "39-310"
        ""         || ""
    }

}
