package domain.project;


import application.dto.AddressDto;
import application.dto.UpdateProjectDetailsDto;
import domain.common.Address;
import domain.survey.QuestionnaireTemplate;
import domain.user.EmailAddress;
import domain.user.User;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@UserDefinedType
@Value
@Builder
public class Details implements Serializable {

    Double budget;
    Surface surface;
    Address location;
    Set<EmailAddress> investorEmailAddresses;
    Questionnaire questionnaire;
    UUID imageId;

    public Details(Double budget, Surface surface, Address location, Set<EmailAddress> investorEmailAddresses, Questionnaire questionnaire, UUID imageId) {
        this.budget = budget;
        this.surface = surface;
        this.location = location;
        this.investorEmailAddresses = investorEmailAddresses != null ? investorEmailAddresses : new HashSet<>();
        this.questionnaire = questionnaire;
        this.imageId = imageId;
    }

    public static Details empty() {
        return Details.builder()
                .investorEmailAddresses(new HashSet<>())
                .budget(0d)
                .location(Address.empty())
                .surface(new Surface(BigDecimal.ZERO))
                .questionnaire(Questionnaire.create(Collections.emptyList()))
                .imageId(null)
                .build();
    }

    public static Details create(UpdateProjectDetailsDto projectDetailsDto) {
        AddressDto location = projectDetailsDto.getLocation();
        return Details.builder()
                .investorEmailAddresses(new HashSet<>())
                .budget(projectDetailsDto.getBudget())
                .location(new Address(location.getNumber(), location.getStreetName(), location.getPostalCode(), location.getCity()))
                .surface(Surface.create(projectDetailsDto.getSurface()))
                .questionnaire(Questionnaire.create(Collections.emptyList()))
                .build();
    }

    public Details update(UpdateProjectDetailsDto updateProjectDetailsDto, QuestionnaireTemplate questionnaireTemplate) {
        AddressDto addressDto = updateProjectDetailsDto.getLocation();
        return Details.builder()
                .budget(updateProjectDetailsDto.getBudget() != null ? updateProjectDetailsDto.getBudget() : budget)
                .location(addressDto != null ? new Address(addressDto.getNumber(), addressDto.getStreetName(), addressDto.getPostalCode(), addressDto.getCity()) : location)
                .surface(updateProjectDetailsDto.getSurface() != null ? new Surface(BigDecimal.valueOf(updateProjectDetailsDto.getSurface())) : surface)
                .investorEmailAddresses(investorEmailAddresses)
                .questionnaire(questionnaire.update(questionnaireTemplate))
                .imageId(imageId)
                .build();
    }

    public void addInvestor(User investor) {
        investorEmailAddresses.add(new EmailAddress(investor.getEmail()));
    }

    public void removeInvestors(User investor) {
        investorEmailAddresses.remove(new EmailAddress(investor.getEmail()));
    }

    public Details updateImage(UUID imageId) {
        return new Details(budget, surface, location, investorEmailAddresses, questionnaire, imageId);
    }
}
