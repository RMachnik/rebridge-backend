package domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

@Table("pictures")
@Data
public class Picture implements Id<UUID>, Serializable {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    @NonNull
    UUID id;
    @NonNull
    ByteBuffer byteBuffer;
}

