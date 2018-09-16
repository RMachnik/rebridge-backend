package domain.project;

import com.datastax.driver.core.DataType;
import com.google.common.collect.Lists;
import domain.DomainExceptions;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@UserDefinedType
@Value
public class Documentation implements WithId<UUID> {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    @NonNull
    UUID id;

    @Indexed
    @CassandraType(type = DataType.Name.UUID)
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

    public void delete(UUID documentId) {
        Optional<Document> first = documents.stream()
                .filter(document -> document.getId().equals(documentId))
                .findFirst();
        Document document = first.orElseThrow(() -> new DomainExceptions.MissingDocumentation(String.format("There is no document with id %s.", documentId)));
        documents.remove(document);
    }
}
