package application.dto;

import domain.project.Comment;
import domain.project.Details;
import domain.project.Questionnaire;
import domain.user.EmailAddress;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class DtoAssemblers {

    public static List<CommentDto> fromCommentsToDtos(List<Comment> comments) {
        return comments.stream()
                .map(comment -> CommentDto.create(comment))
                .collect(toList());
    }

    public static ProjectDetailsDto fromInformationToDto(Details details, List<InvestorDto> investors, String projectId) {
        return ProjectDetailsDto.builder()
                .projectId(projectId)
                .budget(details.getBudget())
                .surface(details.getSurface().getValue().doubleValue())
                .location(AddressDto.create(details.getLocation()))
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

    public static List<AddInvestorDto> fromEmailsToDtos(Set<EmailAddress> investorEmailAddresses) {
        return investorEmailAddresses.stream()
                .map(EmailAddress::getValue)
                .map(AddInvestorDto::new)
                .collect(toList());
    }
}