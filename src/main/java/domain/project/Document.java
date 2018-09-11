package domain.project;

import domain.common.DateTime;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@UserDefinedType
@Value
public class Document implements WithId<UUID> {

    UUID id;
    String name;
    UUID contentId;
    DateTime creationDate;

    public static Document create(String name, UUID contentId) {
        return new Document(
                UUID.randomUUID(),
                name,
                contentId,
                DateTime.now()
        );
    }
}
