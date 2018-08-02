package domain.user;

import domain.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;

@UserDefinedType
@Data
@Builder
@AllArgsConstructor
public class ContactDetails implements Serializable {

    String name;
    String surname;
    String phone;
    Address address;

    public static ContactDetails empty() {
        return ContactDetails.builder()
                .name(StringUtils.EMPTY)
                .surname(StringUtils.EMPTY)
                .address(Address.empty())
                .phone("")
                .build();
    }
}
