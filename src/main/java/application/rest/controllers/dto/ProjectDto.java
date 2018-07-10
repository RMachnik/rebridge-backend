package application.rest.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectDto {
    String id;
    @JsonProperty(required = true)
    String name;

    List<String> inspirations;
}
