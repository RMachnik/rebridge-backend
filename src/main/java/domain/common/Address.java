package domain.common;

import application.dto.AddressDto;
import domain.DomainExceptions.InvalidPostalCode;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@UserDefinedType
@Value
@Builder
public class Address implements Serializable {
    static Pattern POSTAL_CODE_PATTERN = Pattern.compile("[0-9]{2}-[0-9]{3}");

    String number;
    String streetName;
    String postalCode;
    String city;

    public Address(String number, String streetName, String postalCode, String city) {
        this.number = number;
        this.streetName = streetName;
        this.postalCode = isNotBlank(postalCode) ? validatePostalCode(postalCode) : postalCode;
        this.city = city;
    }

    public static Address empty() {
        return Address.builder().build();
    }

    public Address update(AddressDto address) {
        checkArgument(isNotBlank(address.getCity()), "city can't be empty");
        checkArgument(isNotBlank(address.getPostalCode()), "postal code can't be empty");
        checkArgument(isNotBlank(address.getNumber()), "number can't be empty");

        return Address.builder()
                .city(address.getCity())
                .number(address.getNumber())
                .postalCode(validatePostalCode(address.getPostalCode()))
                .streetName(address.getStreetName())
                .build();
    }

    String validatePostalCode(String code) {
        if (!POSTAL_CODE_PATTERN.matcher(code).matches()) {
            throw new InvalidPostalCode(format("postal code %s is invalid", code));
        }
        return code;
    }
}
