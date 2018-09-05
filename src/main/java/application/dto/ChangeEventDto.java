package application.dto;

import domain.event.ChangeEvent;
import lombok.Value;

import java.util.UUID;

@Value
public class ChangeEventDto {

    String id;
    String message;
    String date;
    boolean seen;

    public static ChangeEventDto fromDomain(ChangeEvent changeEvent, UUID userId) {
        return new ChangeEventDto(
                changeEvent.getId().toString(),
                changeEvent.getMessage(),
                changeEvent.getCreationTime().getValue(),
                changeEvent.getSeenBy().contains(userId)
        );
    }
}
