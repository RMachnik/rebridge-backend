package domain.user;

import domain.common.Address;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;

@UserDefinedType
@Data
public class ContactDetails implements Serializable {

    String name;
    String surname;
    String phone;
    Address address;
}
