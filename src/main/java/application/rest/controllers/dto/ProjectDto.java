package application.rest.controllers.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectDto {
    String id;
    String name;
    List<String> inspirationIds;
}
