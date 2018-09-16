package domain.catalogue;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import domain.DomainExceptions.MissingCategory;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
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
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "room name can't be blank");
        return new Room(UUID.randomUUID(), name, Lists.newArrayList());
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public Category findCategory(String categoryId) {
        UUID categoryUUID = UUID.fromString(categoryId);
        return categories.stream()
                .filter(category -> category.getId().equals(categoryUUID))
                .findFirst()
                .orElseThrow(() -> new MissingCategory(String.format("Category with id %s not found.", categoryId)));
    }
}
