package application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvestorDto {
    String name;
    String surname;
    String email;
    String phone;
}
