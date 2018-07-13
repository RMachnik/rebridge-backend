package application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InspirationDetailDto {

    String description;
    String url;
    String pictureId;
    Integer rating;
    List<CommentDto> comments;
}
