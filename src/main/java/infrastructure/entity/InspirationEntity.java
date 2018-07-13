package infrastructure.entity;

import com.datastax.driver.core.DataType;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Table("inspirations")
@Data
public class InspirationEntity implements Serializable {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    UUID id;
    String name;
    InspirationDetailEntity inspirationDetail;
}
