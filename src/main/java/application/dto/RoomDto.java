package application.dto;

import domain.catalogue.Room;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class RoomDto {

    String id;
    String name;
    List<CategoryDto> categories;

    public static RoomDto convert(Room room) {
        return new RoomDto(
                room.getId().toString(),
                room.getName(), room.getCategories()
                .stream()
                .map(CategoryDto::convert)
                .collect(Collectors.toList())
        );
    }
}
