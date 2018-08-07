package domain.project;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.math.BigDecimal;

@UserDefinedType
@Value
@AllArgsConstructor
public class Surface implements Serializable {

    BigDecimal value;

    public static Surface create(double surface) {
        return new Surface(BigDecimal.valueOf(surface));
    }
}