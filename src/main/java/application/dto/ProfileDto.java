package application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class ProfileDto {

    String email;
    String name;
    String surname;
    String phone;
    AddressDto address;

    Set<String> roles;
}
