package application.dto;

import domain.catalogue.Category;
import lombok.Value;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
public class CategoryDto {

    String id;
    String name;
    List<ItemDto> items;

    public static CategoryDto convert(Category category) {
        return new CategoryDto(
                category.getId().toString(),
                category.getName(),
                category.getItems()
                        .stream()
                        .map(ItemDto::convert)
                        .collect(toList())
        );
    }
}
