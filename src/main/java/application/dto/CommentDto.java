package application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {

    String id;
    String content;
    String author;
    String creationDate;
}
