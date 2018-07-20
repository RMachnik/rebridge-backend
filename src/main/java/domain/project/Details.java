package domain.project;


import application.dto.AddressDto;
import application.dto.CreateUpdateProjectDetailsDto;
import domain.common.Address;
import domain.user.EmailAddress;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@UserDefinedType
@Data
@Builder
public class Details implements Serializable {

    Double budget;
    Surface surface;
    Address location;
    List<EmailAddress> investorEmailAddresses;
    Survey survey;

    public static Details create(CreateUpdateProjectDetailsDto projectDetailsDto) {
        AddressDto location = projectDetailsDto.getLocation();
        return Details.builder()
                .investorEmails(projectDetailsDto.getInvestorEmails().stream().map(EmailAddress::new).collect(toList()))
                .budget(projectDetailsDto.getBudget())
                .location(new Address(location.getNumber(), location.getStreetName(), location.getPostalCode(), location.getCity()))
                .surface(new Surface(BigDecimal.valueOf(projectDetailsDto.getSurface())))
                .build();
    }

    public Details update(CreateUpdateProjectDetailsDto updateProjectDetailsDto) {
        AddressDto addressDto = updateProjectDetailsDto.getLocation();
        return Details.builder()
                .budget(updateProjectDetailsDto.getBudget() != null ? updateProjectDetailsDto.getBudget() : budget)
                .location(addressDto != null ? new Address(addressDto.getNumber(), addressDto.getStreetName(), addressDto.getPostalCode(), addressDto.getCity()) : location)
                .surface(updateProjectDetailsDto.getSurface() != null ? new Surface(BigDecimal.valueOf(updateProjectDetailsDto.getSurface())) : surface)
                .investorEmails(updateProjectDetailsDto.getInvestorEmails().stream().map(EmailAddress::new).collect(toList()))
                .build();
    }
}
