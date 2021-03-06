package application.rest;


import application.dto.CommentDto;
import application.dto.CreateOrUpdateDto;
import application.dto.CurrentUser;
import application.service.CommentService;
import domain.project.Comment;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static application.dto.DtoAssemblers.fromCommentsToDtos;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = CommentController.COMMENTS,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class CommentController {

    public static final String COMMENTS = "/projects/{projectId}/inspirations/{inspirationId}/comments";
    CommentService commentService;

    @GetMapping
    ResponseEntity<List<CommentDto>> comments(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String inspirationId) {
        return ResponseEntity.ok(
                fromCommentsToDtos(commentService.findAll(currentUser.getId(), projectId, inspirationId))
        );
    }

    @PostMapping
    ResponseEntity create(
            UriComponentsBuilder builder,
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String inspirationId,
            @RequestBody CreateOrUpdateDto comment) {
        Comment savedComment = commentService.create(currentUser, projectId, inspirationId, comment.getContent());
        UriComponents pathToComment = builder.path(COMMENTS)
                .path("{id}")
                .buildAndExpand(projectId, inspirationId, savedComment.getId());

        return ResponseEntity
                .created(pathToComment.toUri())
                .body(CommentDto.create(savedComment));
    }

    @PutMapping("/{commentId}")
    ResponseEntity update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String inspirationId,
            @PathVariable String commentId,
            @RequestBody CreateOrUpdateDto createOrUpdateDto) {

        CommentDto commentDto = CommentDto
                .builder()
                .id(commentId)
                .content(createOrUpdateDto.getContent())
                .build();

        return ResponseEntity.ok(CommentDto.create(commentService.updateComment(currentUser.getId(), projectId, inspirationId, commentDto)));
    }

    @DeleteMapping("/{commentId}")
    ResponseEntity delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @PathVariable String inspirationId,
            @PathVariable String commentId) {
        commentService.remove(currentUser.getId(), projectId, inspirationId, commentId);
        return ResponseEntity.noContent().build();
    }

}
