package application.service;

import application.dto.CommentDto;
import application.dto.CurrentUser;
import domain.Comment;
import domain.Inspiration;
import domain.Project;
import lombok.Value;

import java.util.List;

@Value
public class CommentService {

    ProjectService projectService;

    public List<Comment> findAll(String userId, String projectId, String inspirationId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Inspiration inspiration = project.findInspiration(inspirationId);
        return inspiration.getInspirationDetail().getComments();
    }

    public Comment create(CurrentUser currentUser, String projectId, String inspirationId, String content) {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        Inspiration inspiration = project.findInspiration(inspirationId);

        Comment comment = Comment.create(currentUser, content);
        inspiration.addComment(comment);

        projectService.save(project);
        return comment;
    }

    public Comment updateComment(String userId, String projectId, String inspirationId, CommentDto commentDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Inspiration inspiration = project.findInspiration(inspirationId);

        Comment updatedComment = inspiration.update(userId, commentDto);
        projectService.save(project);
        return updatedComment;
    }


    public void remove(String userId, String projectId, String inspirationId, String commentId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Inspiration inspiration = project.findInspiration(inspirationId);

        inspiration.removeComment(userId, commentId);

        projectService.save(project);
    }
}
