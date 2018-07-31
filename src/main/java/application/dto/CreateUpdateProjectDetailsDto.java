package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUpdateProjectDetailsDto {
    Double budget;
    Double surface;
    AddressDto location;
    String surveyTemplateId;
}
