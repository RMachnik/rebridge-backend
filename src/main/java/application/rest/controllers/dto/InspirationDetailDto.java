package application.rest.controllers.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InspirationDetailDto {

    String description;
    String url;
    byte[] picture;
    Integer rating;
    List<CommentDto> comments;
}
