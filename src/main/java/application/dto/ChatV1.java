package application.dto;

import domain.project.Chat;
import domain.project.Message;
import lombok.Value;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Value
public class ChatV1 {

    List<ParticipantDto> participants;
    String titleImageUrl;
    List<MessageV1Dto> messageList;
    int newMessagesCount;

    public static ChatV1 create(Chat chat) {
        Set<String> authors = chat.getMessages()
                .stream()
                .map(Message::getAuthor)
                .collect(toSet());
        List<ParticipantDto> participants = authors.stream()
                .map(author -> new ParticipantDto(author, author.split("@")[0], ""))
                .collect(toList());


        String title = "https://a.slack-edge.com/66f9/img/avatars-teams/ava_0001-34.png";
        List<MessageV1Dto> messages = chat.getMessages().stream()
                .map(MessageV1Dto::create)
                .collect(toList());

        return new ChatV1(participants, title, messages, 0);
    }
}
