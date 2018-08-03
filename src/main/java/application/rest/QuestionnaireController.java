package application.rest;

import application.dto.CurrentUser;
import application.dto.DtoAssemblers;
import application.dto.QuestionnaireAnswersDto;
import application.dto.QuestionnaireDto;
import application.service.QuestionnaireService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static application.dto.DtoAssemblers.fromQuestionToDto;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(
        path = QuestionnaireController.QUESTIONNAIRE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class QuestionnaireController {

    public static final String QUESTIONNAIRE = "/projects/{projectId}/questionnaire";

    QuestionnaireService questionnaireService;

    @GetMapping
    ResponseEntity<QuestionnaireDto> get(@AuthenticationPrincipal CurrentUser user, @PathVariable String projectId) {
        return ok(
                questionnaireService.get(user.getId(), projectId)
                        .map(DtoAssemblers::fromQuestionToDto)
                        .orElse(null));
    }

    @PostMapping
    ResponseEntity<QuestionnaireDto> update(
            @AuthenticationPrincipal CurrentUser user,
            @PathVariable String projectId,
            @RequestBody QuestionnaireAnswersDto answerDto) {
        return ok(fromQuestionToDto(questionnaireService.answer(user.getId(), projectId, answerDto)));
    }
}
