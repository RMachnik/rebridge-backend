package application.dto;

import domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

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

    public static ProfileDto create(User user) {
        return builder()
                .email(user.getEmail())
                .name(user.getContactDetails().getName())
                .surname(user.getContactDetails().getSurname())
                .phone(user.getContactDetails().getPhone().getValue())
                .roles(user.getRoles().stream().map(Enum::name).collect(toSet()))
                .address(AddressDto.create(user.getContactDetails().getAddress()))
                .build();
    }
}
