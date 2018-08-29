package application.dto;

import domain.project.Inspiration;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InspirationDto {

    String id;
    String name;
    InspirationDetailDto details;

    public static InspirationDto create(Inspiration inspiration) {
        return builder()
                .id(inspiration.getId().toString())
                .name(inspiration.getName())
                .details(InspirationDetailDto.create(inspiration.getDetails()))
                .build();
    }
}
