package domain.common;

import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@UserDefinedType
@Value
public class DateTime {

    String value;

    private DateTime(LocalDateTime localDateTime) {
        value = localDateTime.format(ISO_DATE_TIME);
    }

    public static DateTime now() {
        return new DateTime(LocalDateTime.now());
    }
}
