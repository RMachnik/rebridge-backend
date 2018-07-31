package application.dto;

import domain.common.Address;
import domain.project.*;
import domain.user.EmailAddress;
import domain.user.Roles;
import domain.user.User;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DtoAssemblers {

    public static ProjectDto fromProjectToDto(Project project) {
        return ProjectDto
                .builder()
                .id(project.getId().toString())
                .name(project.getName())
                .inspirationIds(
                        project.getInspirations().stream()
                                .map((inspiration) -> inspiration.getId().toString())
                                .collect(toList())
                )
                .build();
    }

    public static InspirationDto fromInspirationToDto(Inspiration inspiration) {
        return InspirationDto.builder()
                .id(inspiration.getId().toString())
                .name(inspiration.getName())
                .inspirationDetail(fromInspirationDetailToDto(inspiration.getInspirationDetail()))
                .build();
    }

    private static InspirationDetailDto fromInspirationDetailToDto(InspirationDetail inspirationDetail) {
        return InspirationDetailDto.builder()
                .description(inspirationDetail.getDescription())
                .pictureId(inspirationDetail.getPictureId() == null ? "" : inspirationDetail.getPictureId().toString())
                .rating(inspirationDetail.getRating())
                .url(inspirationDetail.getUrl())
                .comments(fromCommentsToDtos(inspirationDetail.getComments()))
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
                .surname(user.getContactDetails().getSurname())
                .phone(user.getContactDetails().getPhone())
                .build();
    }

    public static ProjectDetailsDto fromInformationToDto(Details details, List<InvestorDto> investors) {
        return ProjectDetailsDto.builder()
                .budget(details.getBudget())
                .surface(details.getSurface().getValue().doubleValue())
                .location(DtoAssemblers.fromAddressToDto(details.getLocation()))
                .investors(investors)
                .build();
    }

    public static SurveyDto fromSurveyToDto(Survey survey) {
        AtomicInteger index = new AtomicInteger(0);
        List<SurveyDto.QuestionDto> questionDtos = survey.getQuestions().stream()
                .map(question -> new SurveyDto.QuestionDto(index.incrementAndGet(), question.getQuestion(), question.getAnswer()))
                .collect(toList());
        return new SurveyDto(questionDtos);
    }

    public static CurrentUser fromUserToCurrentUser(User user, String token) {
        return CurrentUser.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .token(token)
                .roles(user.getRoles().stream().map(Roles::toString).collect(Collectors.toSet()))
                .build();
    }

    public static AddInvestorDto fromEmailsToDtos(Set<EmailAddress> investorEmailAddresses) {
        return AddInvestorDto.builder()
                .investorEmails(investorEmailAddresses.stream()
                        .map(EmailAddress::getValue)
                        .collect(toList()))
                .build();
    }
}
