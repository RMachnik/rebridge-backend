package application.service;

import application.dto.CommentDto;
import application.dto.CurrentUser;
import domain.event.ChangeEvent;
import domain.project.Comment;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.lang.String.format;
import static java.util.UUID.fromString;

@AllArgsConstructor
public class EventableCommentService implements CommentService {

    CommentService commentService;
    ChangeEventService changeEventService;

    @Override
    public List<Comment> findAll(String userId, String projectId, String inspirationId) {
        return commentService.findAll(userId, projectId, inspirationId);
    }

    @Override
    public Comment create(CurrentUser currentUser, String projectId, String inspirationId, String content) {
        Comment comment = commentService.create(currentUser, projectId, inspirationId, content);
        changeEventService.publish(ChangeEvent.create(
                fromString(currentUser.getId()),
                fromString(projectId),
                format("There is a new comment from %s under inspiration.", comment.getAuthor()))
        );
        return comment;
    }

    @Override
    public Comment updateComment(String userId, String projectId, String inspirationId, CommentDto commentDto) {
        return commentService.updateComment(userId, projectId, inspirationId, commentDto);
    }

    @Override
    public void remove(String userId, String projectId, String inspirationId, String commentId) {
        commentService.remove(userId, projectId, inspirationId, commentId);
    }
}
