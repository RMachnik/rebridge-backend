package infrastructure.entity;

import com.datastax.driver.core.DataType;
import infrastructure.InspirationCrudRepository;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Table("products")
@Data
public class ProjectEntity implements Serializable {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    UUID id;
    String name;
    @CassandraType(type = DataType.Name.UUID)
    List<UUID> inspirations;

    public Iterable<InspirationEntity> loadInspirations(InspirationCrudRepository inspirationCrudRepository) {
        return inspirationCrudRepository.findAllById(inspirations);
    }
}
