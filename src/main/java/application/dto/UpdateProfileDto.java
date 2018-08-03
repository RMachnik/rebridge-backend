package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProfileDto {
    String email;
    String name;
    String surname;
    String phone;
    AddressDto address;

}
