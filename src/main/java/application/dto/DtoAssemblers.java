package application.dto;

import domain.project.Comment;
import domain.project.Questionnaire;
import domain.user.EmailAddress;

import java.util.List;
import java.util.Set;

import static application.dto.CommentDto.create;
import static java.util.stream.Collectors.toList;

public class DtoAssemblers {

    public static List<CommentDto> fromCommentsToDtos(List<Comment> comments) {
        return comments.stream()
                .map(comment -> create(comment))
                .collect(toList());
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