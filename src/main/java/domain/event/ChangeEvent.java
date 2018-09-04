package domain.event;

import com.datastax.driver.core.DataType;
import domain.common.DateTime;
import domain.project.WithId;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("changeEvents")
@Value
public class ChangeEvent implements WithId<UUID> {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    @NonNull
    UUID id;
    @NonNull
    UUID projectId;
    @NonNull
    UUID userId;
    @NonNull
    DateTime creationTime;
    @NonNull
    String message;

    public static ChangeEvent create(UUID userId, UUID projectId, String message) {
        return new ChangeEvent(UUID.randomUUID(), projectId, userId, DateTime.now(), message);
    }
}
