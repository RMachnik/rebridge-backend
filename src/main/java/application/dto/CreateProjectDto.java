package application.dto;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateProjectDto {
    @NonNull
    String name;
}
