package application.rest.controllers;


import application.rest.controllers.dto.CommentDto;
import application.rest.controllers.dto.UserDto;
import domain.Comment;
import domain.service.CommentService;
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
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class CommentController {

    public static final String COMMENTS = "/projects/{projectId}/inspirations/{inspirationId}/comments/";
    CommentService commentService;

    @GetMapping
    ResponseEntity<List<CommentDto>> comments(@PathVariable String inspirationId) {
        return ResponseEntity.ok(
                DomainMappers.fromCommentsToDtos(commentService.getAllByInspirationId(inspirationId))
        );
    }

    @PostMapping
    ResponseEntity create(
            UriComponentsBuilder builder,
            @AuthenticationPrincipal UserDto userDto,
            @PathVariable String inspirationId,
            @RequestBody CommentDto comment
    ) {
        Comment savedComment = commentService.create(userDto.getUsername(), inspirationId, comment.getContent());
        UriComponents pathToComment = builder.path(COMMENTS)
                .path("{id}")
                .build();

        return ResponseEntity
                .created(pathToComment.toUri())
                .body(DomainMappers.fromCommentToDto(savedComment));
    }

    @PutMapping("commentId")
    ResponseEntity update(@PathVariable String commentId, @RequestBody CommentDto commentDto) {
        return commentService.getCommentRepository()
                .save(DomainMappers.fromDtoToComment(commentId, commentDto))
                .map(ResponseEntity::ok)
                .getOrElseGet((ex) -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("{commentId}")
    ResponseEntity delete(@PathVariable String inspirationId, @PathVariable String commentId) {
        commentService.remove(inspirationId, commentId);
        return ResponseEntity.noContent().build();
    }

}
