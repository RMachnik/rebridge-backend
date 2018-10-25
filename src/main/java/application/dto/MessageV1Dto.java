package application.dto;

import domain.project.Message;
import lombok.Value;

@Value
public class MessageV1Dto {
    String type;
    String author;
    MessageDataV1Dto data;

    public static MessageV1Dto create(Message message) {
        return new MessageV1Dto("text", message.getAuthor(), new MessageDataV1Dto(message.getContent()));
    }
}
