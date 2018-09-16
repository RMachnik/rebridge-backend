package application.dto;

import domain.catalogue.Catalogue;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class CatalogueDto {
    String id;
    List<RoomDto> rooms;

    public static CatalogueDto convert(Catalogue catalogue) {
        return new CatalogueDto(
                catalogue.getId().toString(),
                catalogue.getRooms()
                        .stream()
                        .map(RoomDto::convert)
                        .collect(Collectors.toList())
        );
    }
}
