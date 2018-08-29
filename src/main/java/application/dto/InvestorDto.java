package application.dto;

import domain.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvestorDto {
    String name;
    String surname;
    String email;
    String phone;

    public static InvestorDto create(User user) {
        return builder()
                .email(user.getEmail())
                .name(user.getContactDetails().getName())
                .surname(user.getContactDetails().getSurname())
                .phone(user.getContactDetails().getPhone().getValue())
                .build();
    }
}
