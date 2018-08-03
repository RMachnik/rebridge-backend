package domain.common;

import application.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;

@UserDefinedType
@Data
@AllArgsConstructor
@Builder
public class Address implements Serializable {
    String number;
    String streetName;
    String postalCode;
    String city;

    public static Address empty() {
        return new Address("", "", "", "");
    }

    public Address update(AddressDto address) {
        return Address.builder()
                .city(address.getCity())
                .number(address.getNumber())
                .postalCode(address.getPostalCode())
                .streetName(address.getStreetName())
                .build();
    }
}
