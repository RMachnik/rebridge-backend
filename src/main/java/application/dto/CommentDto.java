package application.dto;

import domain.project.Comment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {

    String id;
    String content;
    String author;
    String creationDate;

    public static CommentDto create(Comment comment) {
        return builder()
                .id(comment.getId().toString())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .creationDate(comment.getDate().getValue())
                .build();
    }
}
