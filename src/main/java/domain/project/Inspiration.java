package domain.project;

import application.dto.CommentDto;
import application.dto.CreateOrUpdateInspirationDto;
import application.dto.CurrentUser;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.util.UUID;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@UserDefinedType
@Value
@Builder
public class Inspiration implements WithId<UUID>, Serializable {

    @NonNull
    UUID id;

    @NonNull
    String name;

    @NonNull
    InspirationDetail inspirationDetail;

    public static Inspiration create(CreateOrUpdateInspirationDto dto) {
        return Inspiration.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .inspirationDetail(InspirationDetail.create(dto))
                .build();
    }

    public Inspiration update(CreateOrUpdateInspirationDto inspirationDto) {
        return Inspiration
                .builder()
                .id(id)
                .name(isNotBlank(inspirationDto.getName()) ? inspirationDto.getName() : name)
                .inspirationDetail(inspirationDetail.update(inspirationDto))
                .build();
    }

    public void removeComment(String userId, String commentId) {
        Comment comment = findComment(commentId);
        comment.checkUser(userId);
        inspirationDetail.getComments().remove(comment);
    }

    public Comment update(String updatingUserId, CommentDto commentDto) {
        Comment existingComment = findComment(commentDto.getId());
        existingComment.checkUser(updatingUserId);

        Comment updated = existingComment.update(commentDto);

        inspirationDetail.getComments().remove(existingComment);
        inspirationDetail.getComments().add(updated);
        return updated;
    }


    private Comment findComment(String commentId) {
        return inspirationDetail.getComments()
                .stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new DomainExceptions.MissingCommentException(format("there is no comment %s", commentId)));
    }

    public Comment addComment(CurrentUser currentUser, String content) {
        Comment comment = Comment.create(currentUser, content);

        inspirationDetail.add(comment);
        return comment;
    }
}