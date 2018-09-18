package application.dto;

import domain.project.Chat;
import domain.project.Message;
import lombok.Value;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
public class ChatDto {

    String projectId;
    List<MessageDto> messages;

    public static ChatDto create(Chat chat) {
        return new ChatDto(chat.getProjectId().toString(), messages(chat));
    }

    private static List<MessageDto> messages(Chat chat) {
        return chat.getMessages()
                .stream()
                .sorted(Comparator.comparing(Message::getCreationDate))
                .map(MessageDto::create)
                .collect(toList());
    }
}
