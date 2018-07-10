package application.rest.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ExceptionResponse {
    LocalDate timestamp;
    String message;
    String details;
}
