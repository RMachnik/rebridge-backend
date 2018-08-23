package domain.user;

import application.dto.UpdateProfileDto;
import domain.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@UserDefinedType
@Value
@Builder
@AllArgsConstructor
public class ContactDetails implements Serializable {

    String name;
    String surname;
    PhoneNumber phone;
    Address address;

    public static ContactDetails empty() {
        return ContactDetails.builder()
                .name(EMPTY)
                .surname(EMPTY)
                .address(Address.empty())
                .phone(PhoneNumber.empty())
                .build();
    }

    public ContactDetails update(UpdateProfileDto updateProfileDto) {
        checkArgument(isNotBlank(updateProfileDto.getName()), "name can't be black");
        checkArgument(isNotBlank(updateProfileDto.getSurname()), "surname can't be black");


        return ContactDetails.builder()
                .name(updateProfileDto.getName())
                .surname(updateProfileDto.getSurname())
                .phone(PhoneNumber.fromNumber(updateProfileDto.getPhone()))
                .address(address.update(updateProfileDto.getAddress()))
                .build();
    }
}
