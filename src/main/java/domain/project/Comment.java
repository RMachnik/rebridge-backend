package domain.project;

import application.dto.CommentDto;
import application.dto.CurrentUser;
import domain.DomainExceptions.UserActionNotAllowed;
import domain.common.DateTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@UserDefinedType
@Value
@Builder
public class Comment implements WithId<UUID> {

    @NonNull
    UUID id;

    @NonNull
    String userId;

    @NonNull
    String author;

    @NonNull
    String content;

    @NonNull
    DateTime date;

    public static Comment create(CurrentUser currentUser, String content) {
        checkArgument(isNotBlank(content), "Comment can't be empty.");
        return Comment.builder()
                .id(UUID.randomUUID())
                .userId(currentUser.getId())
                .author(currentUser.getEmail())
                .date(DateTime.now())
                .content(content)
                .build();
    }

    void checkUser(String updatingUserId) {
        if (!userId.equals(updatingUserId)) {
            throw new UserActionNotAllowed(format("user %s can't edit this comment %s", userId, id));
        }
    }

    Comment update(CommentDto commentDto) {
        return Comment.builder()
                .id(id)
                .userId(userId)
                .author(author)
                .content(isNotBlank(commentDto.getContent()) ? commentDto.getContent() : content)
                .build();
    }

}
