package domain.project;

import com.datastax.driver.core.DataType;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Table("documentations")
@Value
public class Documentation implements WithId<UUID> {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    @NonNull
    UUID id;
    UUID projectId;
    List<Document> documents;

    public Documentation(UUID id, UUID projectId, List<Document> documents) {
        this.id = id;
        this.projectId = projectId;
        this.documents = Optional.ofNullable(documents).orElse(Lists.newArrayList());
    }

    public static Documentation empty(UUID projectId) {
        return new Documentation(UUID.randomUUID(), projectId, Lists.newArrayList());
    }

    public void addDocument(Document document) {
        documents.add(document);
    }
}
