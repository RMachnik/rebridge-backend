package domain.user;

import domain.DomainExceptions.EmailValidation;
import lombok.Value;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import static java.lang.String.format;

@UserDefinedType
@Value
public class EmailAddress {

    String value;

    public EmailAddress(String value) {
        this.value = validate(value);
    }

    public static String validate(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new EmailValidation(format("this email %s does not meet validation conditions", email));
        }
        return email;
    }
}
