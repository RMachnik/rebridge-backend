package dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InspirationDetailDto {

    String description;
    String url;
    byte[] bytes;
    int rating;
    List<CommentDto> comments;

}
