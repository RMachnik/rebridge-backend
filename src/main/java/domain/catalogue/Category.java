package domain.catalogue;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import domain.DomainExceptions;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@UserDefinedType
@Value
public class Category {

    UUID id;
    String name;
    List<Item> items;

    public Category(UUID id, String name, List<Item> items) {
        this.id = id;
        this.name = name;
        this.items = Optional
                .ofNullable(items)
                .orElse(Lists.newArrayList());
    }

    public static Category create(String name) {
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "category name can't be blank");
        return new Category(UUID.randomUUID(), name, Lists.newArrayList());
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(String itemId) {
        UUID itemUUID = UUID.fromString(itemId);
        Item toBeRemoved = items
                .stream()
                .filter(item -> item.getId().equals(itemUUID))
                .findFirst()
                .orElseThrow(() -> new DomainExceptions.MissingItem(String.format("Item with id %s is missing.", itemId)));
        items.remove(toBeRemoved);
    }
}
