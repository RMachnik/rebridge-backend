package application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SimpleDetailsDto {
    Double budget;
    Double surface;
    AddressDto location;
    String imageId;
}
