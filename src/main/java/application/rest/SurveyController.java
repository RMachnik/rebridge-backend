package application.rest;

import application.dto.CurrentUser;
import application.dto.SurveyAnswersDto;
import application.dto.SurveyDto;
import application.service.SurveyService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static application.dto.DtoAssemblers.fromSurveyToDto;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = SurveyController.SURVEY,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class SurveyController {

    public static final String SURVEY = "/projects/{projectId}/survey";

    SurveyService surveyService;

    @GetMapping
    ResponseEntity<SurveyDto> get(@AuthenticationPrincipal CurrentUser user, @PathVariable String projectId) {
        return ResponseEntity.ok(fromSurveyToDto(surveyService.get(user.getId(), projectId)));
    }

    @PostMapping
    ResponseEntity<SurveyDto> update(
            @AuthenticationPrincipal CurrentUser user,
            @PathVariable String projectId,
            @RequestBody SurveyAnswersDto answerDto) {
        return ResponseEntity.ok(fromSurveyToDto(surveyService.answer(user.getId(), projectId, answerDto)));
    }
}
