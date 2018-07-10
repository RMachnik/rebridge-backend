package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
    String id;
    @JsonProperty(required = true)
    String name;
}
