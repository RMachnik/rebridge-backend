package application.dto;

import domain.project.Details;
import domain.project.Project;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Value
@Builder
public class ProjectDetailsDto {
    String projectId;
    String name;
    Double budget;
    Double surface;
    AddressDto location;
    String questionnaireId;
    List<InvestorDto> investors;

    public static ProjectDetailsDto create(Project project, List<InvestorDto> investors) {
        Details details = project.getDetails();
        return ProjectDetailsDto.builder()
                .projectId(project.getId().toString())
                .name(project.getName())
                .budget(details.getBudget())
                .surface(details.getSurface().getValue().doubleValue())
                .location(AddressDto.create(details.getLocation()))
                .investors(investors)
                .questionnaireId(details.getQuestionnaire().getId().toString())
                .build();
    }

    static SimpleDetailsDto create(Details details) {
        return SimpleDetailsDto.builder()
                .budget(details.getBudget())
                .surface(details.getSurface().getValue().doubleValue())
                .location(AddressDto.create(details.getLocation()))
                .imageId(Optional.ofNullable(details.getImageId()).map(UUID::toString).orElse(""))
                .build();
    }
}
