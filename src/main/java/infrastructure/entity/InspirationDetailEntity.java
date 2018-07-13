package infrastructure.entity;

import com.datastax.driver.core.DataType;
import infrastructure.CommentCrudRepository;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Data
public class InspirationDetailEntity implements Serializable {

    String description;
    String url;
    ByteBuffer picture;
    Integer rating;

    @CassandraType(type = DataType.Name.UUID)
    List<UUID> comments;

    public Iterable<CommentEntity> loadComments(CommentCrudRepository repository) {
        return repository.findAllById(comments);
    }
}
