package application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectDetailsDto {
    String projectId;
    Double budget;
    Double surface;
    AddressDto location;
    String questionnaireId;
    List<InvestorDto> investors;
}
