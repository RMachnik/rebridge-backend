package application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InspirationDto {

    String id;
    String name;
    InspirationDetailDto details;
}
