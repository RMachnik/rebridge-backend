package application.service;

import application.dto.CommentDto;
import application.dto.CurrentUser;
import domain.project.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll(String userId, String projectId, String inspirationId);

    Comment create(CurrentUser currentUser, String projectId, String inspirationId, String content);

    Comment updateComment(String userId, String projectId, String inspirationId, CommentDto commentDto);

    void remove(String userId, String projectId, String inspirationId, String commentId);
}
