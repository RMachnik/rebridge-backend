package application.dto;

import domain.Comment;
import domain.Inspiration;
import domain.InspirationDetail;
import domain.Project;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DomainMappers {

    public static ProjectDto fromProjectToDto(Project project) {
        return ProjectDto
                .builder()
                .id(project.getId())
                .name(project.getName())
                .inspirationIds(
                        project.getInspirations().stream()
                                .map(Inspiration::getId)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static InspirationDto fromInspirationToDto(Inspiration inspiration) {
        return InspirationDto.builder()
                .id(inspiration.getId())
                .name(inspiration.getName())
                .inspirationDetail(fromInspirationDetailToDto(inspiration.getInspirationDetail()))
                .build();
    }

    private static InspirationDetailDto fromInspirationDetailToDto(InspirationDetail inspirationDetail) {
        return InspirationDetailDto.builder()
                .description(inspirationDetail.getDescription())
                .picture(inspirationDetail.getPicture())
                .rating(inspirationDetail.getRating())
                .url(inspirationDetail.getUrl())
                .comments(fromCommentsToDtos(inspirationDetail.getComments()))
                .build();
    }

    public static List<CommentDto> fromCommentsToDtos(List<Comment> comments) {
        return comments.stream()
                .map(comment -> fromCommentToDto(comment))
                .collect(toList());
    }

    public static CommentDto fromCommentToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .creationDate(comment.getDate())
                .build();
    }
}
