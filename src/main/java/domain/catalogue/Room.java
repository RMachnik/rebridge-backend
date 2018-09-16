package domain.catalogue;

import com.google.common.collect.Lists;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@UserDefinedType
@Value
public class Room {

    UUID id;
    String name;
    List<Category> categories;

    public Room(UUID id, String name, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.categories = Optional
                .ofNullable(categories)
                .orElse(Lists.newArrayList());
    }

    public static Room create(String name) {
        return new Room(UUID.randomUUID(), name, Lists.newArrayList());
    }

    public void addCategory(Category category) {
        categories.add(category);
    }
}
