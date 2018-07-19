package domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;

@UserDefinedType
@Data
@AllArgsConstructor
public class Address implements Serializable {
    String number;
    String streetName;
    String postalCode;
    String city;
}
