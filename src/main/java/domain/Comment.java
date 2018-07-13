package domain;

import application.dto.CommentDto;
import application.dto.CurrentUser;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@UserDefinedType
@Data
@Builder
public class Comment implements Id<UUID>, Serializable {

    @NotNull
    UUID id;
    @NonNull
    String userId;
    @NonNull
    String author;
    @NonNull
    String content;
    @NonNull
    String date;

    public static Comment create(CurrentUser currentUser, String content) {
        return Comment.builder()
                .id(UUID.randomUUID())
                .userId(currentUser.getId())
                .author(currentUser.getUsername())
                .date(LocalDateTime.now().toString())
                .content(content)
                .build();
    }

    void checkUser(String updatingUserId) {
        if (userId.equals(updatingUserId)) {
            throw new DomainExceptions.UserActionNotAllowed(format("user %s can't edit this comment %s", userId, id));
        }
    }

    Comment update(CommentDto commentDto) {
        return domain.Comment.builder()
                .id(id)
                .userId(userId)
                .author(author)
                .content(isNotBlank(commentDto.getContent()) ? commentDto.getContent() : content)
                .build();
    }

}
