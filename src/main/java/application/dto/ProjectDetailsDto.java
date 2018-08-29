package application.dto;

import domain.project.Details;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
public class ProjectDetailsDto {
    String projectId;
    Double budget;
    Double surface;
    AddressDto location;
    String questionnaireId;
    List<InvestorDto> investors;

    static SimpleDetailsDto create(Details details) {
        return SimpleDetailsDto.builder()
                .budget(details.getBudget())
                .surface(details.getSurface().getValue().doubleValue())
                .location(AddressDto.create(details.getLocation()))
                .imageId(Optional.ofNullable(details.getImageId()).map(UUID::toString).orElse(""))
                .build();
    }
}
