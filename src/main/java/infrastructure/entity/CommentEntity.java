package infrastructure.entity;

import com.datastax.driver.core.DataType;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Data
public class CommentEntity implements Serializable {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    UUID id;
    String userId;
    String author;
    String content;
    String date;
}
