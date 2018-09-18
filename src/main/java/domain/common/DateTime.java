package domain.common;

import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@UserDefinedType
@Value
public class DateTime implements Comparable<DateTime> {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
    String value;

    public static DateTime now() {
        return new DateTime(LocalDateTime.now().format(ISO_DATE_TIME));
    }

    public String simpleDate() {
        return LocalDateTime.parse(value).format(FORMATTER);
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.parse(value, ISO_DATE_TIME);
    }

    @Override
    public int compareTo(DateTime o) {
        return getDateTime().compareTo(o.getDateTime());
    }
}
