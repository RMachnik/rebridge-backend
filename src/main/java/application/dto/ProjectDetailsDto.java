package application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectDetailsDto {
    Double budget;
    Double surface;
    AddressDto location;
    String surveyTemplateId;
    List<InvestorDto> investors;
}
