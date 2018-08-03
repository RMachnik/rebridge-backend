package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProjectDetailsDto {
    Double budget;
    Double surface;
    AddressDto location;
    String questionnaireId;
}
