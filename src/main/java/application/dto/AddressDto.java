package application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    String number;
    String streetName;
    String postalCode;
    String city;
}
