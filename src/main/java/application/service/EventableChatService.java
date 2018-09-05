package application.service;

import application.dto.CreateMessageDto;
import application.dto.CurrentUser;
import domain.event.ChangeEvent;
import domain.project.Message;
import lombok.AllArgsConstructor;

import static java.lang.String.format;
import static java.util.UUID.fromString;

@AllArgsConstructor
public class EventableChatService implements ChatService {

    ChatService chatService;
    ChangeEventService changeEventService;

    @Override
    public Message postMessage(CurrentUser currentUser, String projectId, CreateMessageDto messageDto) {
        Message message = chatService.postMessage(currentUser, projectId, messageDto);
        changeEventService.publish(ChangeEvent.create(
                fromString(currentUser.getId()),
                fromString(projectId),
                format("There is new message from %s.", currentUser.getUsername())
                )
        );
        return message;
    }
}
