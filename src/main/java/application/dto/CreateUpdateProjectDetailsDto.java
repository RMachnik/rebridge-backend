package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateUpdateProjectDetailsDto {
    Double budget;
    Double surface;
    AddressDto location;
    String surveyTemplateId;
    List<String> investorEmails;
}
