package domain.project;

import application.dto.CreateMessageDto;
import application.dto.CurrentUser;
import domain.common.DateTime;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@UserDefinedType
@Value
public class Message implements WithId<UUID> {
    UUID id;
    String author;
    String content;
    DateTime creationDate;

    public static Message create(CurrentUser currentUser, CreateMessageDto messageDto) {
        return new Message(UUID.randomUUID(), currentUser.getEmail(), messageDto.getContent(), DateTime.now());
    }
}
