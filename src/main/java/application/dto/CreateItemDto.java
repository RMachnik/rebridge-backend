package application.dto;

import lombok.Value;

@Value
public class CreateItemDto {

    String description;
    String size;
    double quantity;
    double prize;
    String additionalInfo;

}
