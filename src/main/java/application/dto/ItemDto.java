package application.dto;

import domain.catalogue.Item;
import lombok.Value;

@Value
public class ItemDto {
    String id;

    public static ItemDto convert(Item item) {
        return new ItemDto(item.getId().toString());
    }
}
