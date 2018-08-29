package domain.project;

import com.google.common.collect.Lists;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;
import java.util.UUID;

@UserDefinedType
@Value
public class Chat {

    UUID id;
    UUID projectId;
    List<Message> messages;

    public static Chat empty(UUID projectId) {
        return new Chat(UUID.randomUUID(), projectId, Lists.newArrayList());
    }

    public void postMessage(Message message) {
        messages.add(message);
    }
}