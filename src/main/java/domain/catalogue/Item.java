package domain.catalogue;

import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@UserDefinedType
@Value
public class Item {

    UUID id;
    String description;
    String size;
    double quantity;
    double prize;
    String additionalInfo;
    boolean approved;

}
