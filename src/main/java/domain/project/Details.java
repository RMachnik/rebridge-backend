package domain.project;


import application.dto.AddressDto;
import application.dto.CreateUpdateProjectDetailsDto;
import domain.common.Address;
import domain.user.EmailAddress;
import domain.user.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@UserDefinedType
@Data
@Builder
public class Details implements Serializable {

    Double budget;
    Surface surface;
    Address location;
    Set<EmailAddress> investorEmailAddresses;
    Questionnaire questionnaire;

    public Details(Double budget, Surface surface, Address location, Set<EmailAddress> investorEmailAddresses, Questionnaire questionnaire) {
        this.budget = budget;
        this.surface = surface;
        this.location = location;
        this.investorEmailAddresses = investorEmailAddresses != null ? investorEmailAddresses : new HashSet<>();
        this.questionnaire = questionnaire;
    }

    public static Details empty() {
        return Details.builder()
                .investorEmailAddresses(new HashSet<>())
                .budget(0d)
                .location(Address.empty())
                .surface(new Surface(BigDecimal.ZERO))
                .build();
    }

    public static Details create(CreateUpdateProjectDetailsDto projectDetailsDto, Questionnaire questionnaire) {
        AddressDto location = projectDetailsDto.getLocation();
        return Details.builder()
                .investorEmailAddresses(new HashSet<>())
                .budget(projectDetailsDto.getBudget())
                .location(new Address(location.getNumber(), location.getStreetName(), location.getPostalCode(), location.getCity()))
                .surface(new Surface(BigDecimal.valueOf(projectDetailsDto.getSurface())))
                .questionnaire(questionnaire)
                .build();
    }

    public Details update(CreateUpdateProjectDetailsDto updateProjectDetailsDto) {
        AddressDto addressDto = updateProjectDetailsDto.getLocation();
        return Details.builder()
                .budget(updateProjectDetailsDto.getBudget() != null ? updateProjectDetailsDto.getBudget() : budget)
                .location(addressDto != null ? new Address(addressDto.getNumber(), addressDto.getStreetName(), addressDto.getPostalCode(), addressDto.getCity()) : location)
                .surface(updateProjectDetailsDto.getSurface() != null ? new Surface(BigDecimal.valueOf(updateProjectDetailsDto.getSurface())) : surface)
                .investorEmailAddresses(investorEmailAddresses)
                .build();
    }

    public void addInvestors(List<User> investors) {
        List<EmailAddress> emails = convertToEmails(investors);
        investorEmailAddresses.addAll(emails);
    }

    public void removeInvestors(List<User> investors) {
        List<EmailAddress> emails = convertToEmails(investors);
        investorEmailAddresses.removeAll(emails);
    }

    private List<EmailAddress> convertToEmails(List<User> investors) {
        return investors.stream()
                .map(User::getEmail)
                .map(EmailAddress::new)
                .collect(toList());
    }
}
