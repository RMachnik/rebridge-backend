package domain.project;


import application.dto.AddressDto;
import application.dto.UpdateProjectDetailsDto;
import domain.common.Address;
import domain.survey.QuestionnaireTemplate;
import domain.user.EmailAddress;
import domain.user.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    public static Details empty(QuestionnaireTemplate questionnaireTemplate) {
        return Details.builder()
                .investorEmailAddresses(new HashSet<>())
                .budget(0d)
                .location(Address.empty())
                .surface(new Surface(BigDecimal.ZERO))
                .questionnaire(Questionnaire.create(questionnaireTemplate.getQuestions()))
                .build();
    }

    public static Details create(UpdateProjectDetailsDto projectDetailsDto, Questionnaire questionnaire) {
        AddressDto location = projectDetailsDto.getLocation();
        return Details.builder()
                .investorEmailAddresses(new HashSet<>())
                .budget(projectDetailsDto.getBudget())
                .location(new Address(location.getNumber(), location.getStreetName(), location.getPostalCode(), location.getCity()))
                .surface(Surface.create(projectDetailsDto.getSurface()))
                .questionnaire(questionnaire)
                .build();
    }

    public Details update(UpdateProjectDetailsDto updateProjectDetailsDto) {
        AddressDto addressDto = updateProjectDetailsDto.getLocation();
        return Details.builder()
                .budget(updateProjectDetailsDto.getBudget() != null ? updateProjectDetailsDto.getBudget() : budget)
                .location(addressDto != null ? new Address(addressDto.getNumber(), addressDto.getStreetName(), addressDto.getPostalCode(), addressDto.getCity()) : location)
                .surface(updateProjectDetailsDto.getSurface() != null ? new Surface(BigDecimal.valueOf(updateProjectDetailsDto.getSurface())) : surface)
                .investorEmailAddresses(investorEmailAddresses)
                .questionnaire(questionnaire)
                .build();
    }

    public void addInvestor(User investor) {
        investorEmailAddresses.add(new EmailAddress(investor.getEmail()));
    }

    public void removeInvestors(User investor) {
        investorEmailAddresses.remove(new EmailAddress(investor.getEmail()));
    }

}
