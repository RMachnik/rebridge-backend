package application.rest.controllers;

import application.rest.controllers.dto.CommentDto;
import application.rest.controllers.dto.InspirationDetailDto;
import application.rest.controllers.dto.InspirationDto;
import application.rest.controllers.dto.ProjectDto;
import domain.Comment;
import domain.Inspiration;
import domain.InspirationDetail;
import domain.Project;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DomainMappers {

    static ProjectDto fromProjectToDto(Project project) {
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

    static Project fromDtoToProject(ProjectDto projectDto) {
        return Project.builder()
                .id(projectDto.getId())
                .name(projectDto.getName())
                .build();
    }

    static Inspiration fromDtoToInspiration(InspirationDto inspirationDto) {
        return Inspiration.builder()
                .id(inspirationDto.getId())
                .name(inspirationDto.getName())
                .inspirationDetail(fromDtoToInspirationDetail(inspirationDto.getInspirationDetail()))
                .build();
    }

    private static InspirationDetail fromDtoToInspirationDetail(InspirationDetailDto inspirationDetailDto) {
        return InspirationDetail.builder()
                .description(inspirationDetailDto.getDescription())
                .picture(inspirationDetailDto.getPicture())
                .comments(fromDtosToComments(inspirationDetailDto.getComments()))
                .url(inspirationDetailDto.getUrl())
                .build();
    }

    private static List<Comment> fromDtosToComments(List<CommentDto> commentDtos) {
        return commentDtos.stream()
                .map(DomainMappers::fromDtoToComment)
                .collect(toList());
    }

    static Comment fromDtoToComment(CommentDto commentDto) {
        return Comment.builder()
                .author(commentDto.getAuthor())
                .content(commentDto.getContent())
                .date(commentDto.getCreationDate())
                .build();
    }

    static InspirationDto fromInspirationToDto(Inspiration inspiration) {
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

    static List<CommentDto> fromCommentsToDtos(List<Comment> comments) {
        return comments.stream()
                .map(comment -> CommentDto.builder()
                        .id(comment.getId())
                        .author(comment.getAuthor())
                        .content(comment.getContent())
                        .creationDate(comment.getDate())
                        .build())
                .collect(toList());
    }
}
