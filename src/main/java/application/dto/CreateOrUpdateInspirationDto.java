package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrUpdateInspirationDto {
    String name;
    String description;
    String url;
    int rating;
}
