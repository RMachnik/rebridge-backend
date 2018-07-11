package domain;

import application.rest.controllers.dto.CommentDto;
import application.rest.controllers.dto.UserDto;
import domain.service.DomainExceptions;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Value
@Builder
public class Comment implements Id<String> {

    @NotNull
    String id;
    @NonNull
    String userId;
    @NonNull
    String author;
    @NonNull
    String content;
    @NonNull
    String date;

    public static Comment create(UserDto userDto, String content) {
        return Comment.builder()
                .id(UUID.randomUUID().toString())
                .userId(userDto.getId())
                .author(userDto.getUsername())
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
                .id(commentDto.getId())
                .userId(userId)
                .author(author)
                .content(isNotBlank(commentDto.getContent()) ? commentDto.getContent() : content)
                .build();
    }

}
