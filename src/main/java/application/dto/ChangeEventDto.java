package application.dto;

import domain.event.ChangeEvent;
import lombok.Value;

@Value
public class ChangeEventDto {

    String message;
    String date;

    public static ChangeEventDto fromDomain(ChangeEvent changeEvent) {
        return new ChangeEventDto(changeEvent.getMessage(), changeEvent.getCreationTime().getValue());
    }
}
