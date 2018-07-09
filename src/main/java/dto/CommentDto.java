package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {

    String content;
    String author;
    String creationDate;
}
