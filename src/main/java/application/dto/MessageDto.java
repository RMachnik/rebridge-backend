package application.dto;

import domain.project.Message;
import lombok.Value;

@Value
public class MessageDto {

    String author;
    String content;
    String creationDate;

    public static MessageDto create(Message message) {
        return new MessageDto(message.getAuthor(), message.getContent(), message.getCreationDate().simpleDate());
    }
}
