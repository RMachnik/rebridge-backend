package domain.project;

import com.datastax.driver.core.DataType;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;
import java.util.UUID;

@Table("pictures")
@Value
public class Image implements WithId<UUID> {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    @NonNull
    UUID id;
    String name;
    String mimeType;

    @NonNull
    ByteBuffer byteBuffer;


    public static Image create(String name, String mimeType, ByteBuffer data) {
        return new Image(UUID.randomUUID(), name, mimeType, data);
    }
}

