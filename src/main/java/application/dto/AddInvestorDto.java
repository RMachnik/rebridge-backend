package application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AddInvestorDto {
    List<String> investorEmails;
}
