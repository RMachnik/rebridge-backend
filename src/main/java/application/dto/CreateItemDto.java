package application.dto;

import lombok.Value;

@Value
public class CreateItemDto {

    String description;
    String sizing;
    double quantity;
    double prize;
    String additionalInfo;

}
