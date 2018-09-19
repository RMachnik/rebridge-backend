package application.dto;

import domain.catalogue.Item;
import lombok.Value;

@Value
public class ItemDto {

    String id;
    String description;
    String sizing;
    double quantity;
    double prize;
    String additionalInfo;
    boolean approved;

    public static ItemDto convert(Item item) {
        return new ItemDto(
                item.getId().toString(),
                item.getDescription(),
                item.getSize(),
                item.getQuantity(),
                item.getPrize(),
                item.getAdditionalInfo(),
                item.isApproved()
        );
    }
}
