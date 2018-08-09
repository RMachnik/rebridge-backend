package application.dto;

import domain.common.Address;
import domain.project.*;
import domain.survey.QuestionnaireTemplate;
import domain.user.EmailAddress;
import domain.user.Roles;
import domain.user.User;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class DtoAssemblers {

    public static ProjectDto fromProjectToDto(Project project) {
        return ProjectDto
                .builder()
                .id(project.getId().toString())
                .name(project.getName())
                .questionnaireTemplateId(project.getQuestionnaireTemplateId().toString())
                .details(fromDetailsToSimpleDto(project.getDetails()))
                .build();
    }

    private static SimpleDetailsDto fromDetailsToSimpleDto(Details details) {
        return SimpleDetailsDto.builder()
                .budget(details.getBudget())
                .surface(details.getSurface().getValue().doubleValue())
                .location(fromAddressToDto(details.getLocation()))
                .build();
    }

    public static InspirationDto fromInspirationToDto(Inspiration inspiration) {
        return InspirationDto.builder()
                .id(inspiration.getId().toString())
                .name(inspiration.getName())
                .details(fromInspirationDetailToDto(inspiration.getDetails()))
                .build();
    }

    private static InspirationDetailDto fromInspirationDetailToDto(InspirationDetails inspirationDetails) {
        return InspirationDetailDto.builder()
                .description(inspirationDetails.getDescription())
                .pictureId(inspirationDetails.getPictureId() == null ? "" : inspirationDetails.getPictureId().toString())
                .rating(inspirationDetails.getRating())
                .url(inspirationDetails.getUrl())
                .comments(fromCommentsToDtos(inspirationDetails.getComments()))
                .build();
    }

    public static List<CommentDto> fromCommentsToDtos(List<Comment> comments) {
        return comments.stream()
                .map(comment -> fromCommentToDto(comment))
                .collect(toList());
    }

    public static CommentDto fromCommentToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId().toString())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .creationDate(comment.getDate())
                .build();
    }

    public static AddressDto fromAddressToDto(Address address) {
        return AddressDto.builder()
                .city(address.getCity())
                .number(address.getNumber())
                .postalCode(address.getPostalCode())
                .streetName(address.getStreetName())
                .build();
    }

    public static InvestorDto fromUserToInvestor(User user) {
        return InvestorDto
                .builder()
                .email(user.getEmail())
                .name(user.getContactDetails().getName())
                .surname(user.getContactDetails().getSurname())
                .phone(user.getContactDetails().getPhone())
                .build();
    }

    public static ProjectDetailsDto fromInformationToDto(Details details, List<InvestorDto> investors, String projectId) {
        return ProjectDetailsDto.builder()
                .projectId(projectId)
                .budget(details.getBudget())
                .surface(details.getSurface().getValue().doubleValue())
                .location(DtoAssemblers.fromAddressToDto(details.getLocation()))
                .investors(investors)
                .questionnaireId(details.getQuestionnaire().getId().toString())
                .build();
    }

    public static QuestionnaireDto fromQuestionToDto(Questionnaire questionnaire) {
        List<QuestionnaireDto.QuestionDto> questionDtos = questionnaire.getQuestions().stream()
                .map(question -> new QuestionnaireDto.QuestionDto(question.getId(), question.getQuestion(), question.getAnswer()))
                .collect(toList());
        return new QuestionnaireDto(questionDtos);
    }

    public static CurrentUser fromUserToCurrentUser(User user, String token) {
        return CurrentUser.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .token(token)
                .roles(user.getRoles().stream().map(Roles::toString).collect(toSet()))
                .build();
    }

    public static List<AddInvestorDto> fromEmailsToDtos(Set<EmailAddress> investorEmailAddresses) {
        return investorEmailAddresses.stream()
                .map(EmailAddress::getValue)
                .map(AddInvestorDto::new)
                .collect(toList());
    }

    public static QuestionnaireTemplateDto fromSurveyTemplateToDto(QuestionnaireTemplate questionnaireTemplate) {
        return new QuestionnaireTemplateDto(questionnaireTemplate.getId().toString(), questionnaireTemplate.getName(), questionnaireTemplate.getQuestions());
    }

    public static ProfileDto fromUserToProfileDto(User user) {
        return ProfileDto.builder()
                .email(user.getEmail())
                .name(user.getContactDetails().getName())
                .surname(user.getContactDetails().getSurname())
                .phone(user.getContactDetails().getPhone())
                .roles(user.getRoles().stream().map(Enum::name).collect(toSet()))
                .address(fromAddressToDto(user.getContactDetails().getAddress()))
                .build();
    }
}