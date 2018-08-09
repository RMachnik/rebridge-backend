package application.service;

import application.dto.CommentDto;
import application.dto.CurrentUser;
import domain.project.Comment;
import domain.project.Inspiration;
import domain.project.Project;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class CommentService {

    ProjectService projectService;

    public List<Comment> findAll(String userId, String projectId, String inspirationId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Inspiration inspiration = project.findInspiration(UUID.fromString(inspirationId));
        return inspiration.getDetails().getComments();
    }

    public Comment create(CurrentUser currentUser, String projectId, String inspirationId, String content) {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        Inspiration inspiration = project.findInspiration(UUID.fromString(inspirationId));

        Comment comment = inspiration.addComment(currentUser, content);

        projectService.save(project);
        return comment;
    }

    public Comment updateComment(String userId, String projectId, String inspirationId, CommentDto commentDto) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Inspiration inspiration = project.findInspiration(UUID.fromString(inspirationId));

        Comment updatedComment = inspiration.update(userId, commentDto);
        projectService.save(project);
        return updatedComment;
    }


    public void remove(String userId, String projectId, String inspirationId, String commentId) {
        Project project = projectService.findByUserIdAndProjectId(userId, projectId);
        Inspiration inspiration = project.findInspiration(UUID.fromString(inspirationId));

        inspiration.removeComment(userId, commentId);

        projectService.save(project);
    }
}
