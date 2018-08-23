package domain.common;

import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@UserDefinedType
@Value
public class DateTime {

    String value;

    public static DateTime now() {
        return new DateTime(LocalDateTime.now().format(ISO_DATE_TIME));
    }
}
