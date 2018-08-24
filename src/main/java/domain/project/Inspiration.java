package domain.project;

import application.dto.CommentDto;
import application.dto.CreateOrUpdateInspirationDto;
import application.dto.CurrentUser;
import domain.project.DomainExceptions.MissingCommentException;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@UserDefinedType
@Data
@Builder
public class Inspiration implements WithId<UUID> {

    @NonNull
    UUID id;

    @NonNull
    String name;

    @NonNull
    InspirationDetails details;

    public static Inspiration create(CreateOrUpdateInspirationDto dto) {
        checkArgument(isNotBlank(dto.getName()), "Inspiration name can't be empty.");
        return Inspiration.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .details(InspirationDetails.create(dto))
                .build();
    }

    public Inspiration update(CreateOrUpdateInspirationDto inspirationDto) {
        return Inspiration
                .builder()
                .id(id)
                .name(isNotBlank(inspirationDto.getName()) ? inspirationDto.getName() : name)
                .details(details.update(inspirationDto))
                .build();
    }

    public void removeComment(String userId, String commentId) {
        Comment comment = findComment(commentId);
        comment.checkUser(userId);
        details.getComments().remove(comment);
    }

    public Comment update(String updatingUserId, CommentDto commentDto) {
        Comment existingComment = findComment(commentDto.getId());
        existingComment.checkUser(updatingUserId);

        Comment updated = existingComment.update(commentDto);

        details.getComments().remove(existingComment);
        details.getComments().add(updated);
        return updated;
    }


    private Comment findComment(String commentId) {
        UUID id = UUID.fromString(commentId);
        return details.getComments()
                .stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new MissingCommentException(format("there is no comment %s", commentId)));
    }

    public Comment addComment(CurrentUser currentUser, String content) {
        Comment comment = Comment.create(currentUser, content);
        details.add(comment);
        return comment;
    }

    public void updatePictureId(UUID pictureId) {
        this.details = details.updatePictureId(pictureId);
    }
}