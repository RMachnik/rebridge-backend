package domain.event;

import com.datastax.driver.core.DataType;
import com.google.common.collect.Sets;
import domain.common.DateTime;
import domain.project.WithId;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Set;
import java.util.UUID;

import static java.util.Optional.ofNullable;

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
    @NonNull
    Set<UUID> seenBy;

    public ChangeEvent(UUID id, UUID projectId, UUID userId, DateTime creationTime, String message, Set<UUID> seenBy) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.creationTime = creationTime;
        this.message = message;
        this.seenBy = ofNullable(seenBy).orElse(Sets.newHashSet());
    }

    public static ChangeEvent create(UUID userId, UUID projectId, String message) {
        return new ChangeEvent(UUID.randomUUID(), projectId, userId, DateTime.now(), message, Sets.newHashSet());
    }

    public void markAsReed(String userId) {
        seenBy.add(UUID.fromString(userId));
    }
}
