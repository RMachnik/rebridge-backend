package application.rest;


import application.dto.CommentDto;
import application.dto.DtoAssemblers;
import application.dto.UserDto;
import application.service.CommentService;
import domain.Comment;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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
            @AuthenticationPrincipal UserDto userDto,
            @PathVariable String projectId,
            @PathVariable String inspirationId) {
        return ResponseEntity.ok(
                DtoAssemblers.fromCommentsToDtos(commentService.findAll(userDto.getId(), projectId, inspirationId))
        );
    }

    @PostMapping
    ResponseEntity create(
            UriComponentsBuilder builder,
            @AuthenticationPrincipal UserDto userDto,
            @PathVariable String projectId,
            @PathVariable String inspirationId,
            @RequestBody CommentDto comment) {
        Comment savedComment = commentService.create(userDto, projectId, inspirationId, comment.getContent());
        UriComponents pathToComment = builder.path(COMMENTS)
                .path("{id}")
                .build();

        return ResponseEntity
                .created(pathToComment.toUri())
                .body(DtoAssemblers.fromCommentToDto(savedComment));
    }

    @PutMapping("/{commentId}")
    ResponseEntity update(
            @AuthenticationPrincipal UserDto userDto,
            @PathVariable String projectId,
            @PathVariable String inspirationId,
            @PathVariable String commentId,
            @RequestBody CommentDto commentDto) {
        commentDto.setId(commentId);
        return ResponseEntity.ok(commentService.updateComment(userDto.getId(), projectId, inspirationId, commentDto));
    }

    @DeleteMapping("/{commentId}")
    ResponseEntity delete(
            @AuthenticationPrincipal UserDto userDto,
            @PathVariable String projectId,
            @PathVariable String inspirationId,
            @PathVariable String commentId) {
        commentService.remove(userDto.getId(), projectId, inspirationId, commentId);
        return ResponseEntity.noContent().build();
    }

}
