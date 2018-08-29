package application.dto;

import domain.common.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    String number;
    String streetName;
    String postalCode;
    String city;

    public static AddressDto create(Address address) {
        return builder()
                .city(address.getCity())
                .number(address.getNumber())
                .postalCode(address.getPostalCode())
                .streetName(address.getStreetName())
                .build();
    }
}
