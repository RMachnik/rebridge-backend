package domain.catalogue;

import com.google.common.base.Preconditions;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
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

    public static Item create(String description, String size, double quantity, double prize, String additionalInfo) {
        Preconditions.checkArgument(StringUtils.isNotBlank(description), "description can't be blank");
        return new Item(UUID.randomUUID(), description, size, quantity, prize, additionalInfo, false);
    }
}
