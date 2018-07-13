package domain;

import application.dto.CommentDto;
import application.dto.InspirationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Value
@Builder
public class Inspiration implements Id<UUID> {

    @NonNull
    UUID id;
    @JsonProperty(required = true)
    String name;
    @NotNull
    InspirationDetail inspirationDetail;

    public static Inspiration create(String name) {
        return Inspiration.builder()
                .id(UUID.randomUUID())
                .name(name)
                .inspirationDetail(InspirationDetail.createDefault())
                .build();
    }

    public Inspiration update(InspirationDto inspirationDto) {
        return Inspiration
                .builder()
                .id(id)
                .name(isNotBlank(inspirationDto.getName()) ? inspirationDto.getName() : name)
                .inspirationDetail(inspirationDetail.update(inspirationDto.getInspirationDetail()))
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

    public void addComment(Comment comment) {
        inspirationDetail.add(comment);
    }
}