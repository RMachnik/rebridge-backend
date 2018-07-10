package application.rest.controllers;


import application.rest.controllers.dto.CommentDto;
import application.rest.controllers.dto.UserDto;
import domain.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/projects/{projectId}/inspirations/{inspirationId}/comments/")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class CommentController {

    CommentService commentService;

    @GetMapping
    ResponseEntity<List<CommentDto>> comments(@PathVariable String inspirationId) {
        return ResponseEntity.ok(
                DomainMappers.fromCommentsToDtos(commentService.getAllByInspirationId(inspirationId))
        );
    }

    @PostMapping
    ResponseEntity create(@AuthenticationPrincipal UserDto userDto, @PathVariable String inspirationId, String comment) {
        return ResponseEntity.created(
                URI
                        .create(
                                commentService.create(userDto.getUsername(), inspirationId, comment)
                        )
        ).build();
    }

    @PutMapping("commentId")
    ResponseEntity update(@PathVariable String commentId, CommentDto commentDto) {
        commentDto.setId(commentId);
        return commentService.getCommentRepository()
                .save(DomainMappers.fromDtoToComment(commentDto))
                .map(ResponseEntity::ok)
                .getOrElseGet((ex) -> ResponseEntity.badRequest().build());
    }

}
