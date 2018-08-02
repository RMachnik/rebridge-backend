package application.rest;

import application.dto.DtoAssemblers;
import application.dto.SurveyTemplateDto;
import application.service.SurveyTemplateService;
import domain.survey.SurveyTemplate;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static application.dto.DtoAssemblers.fromSurveyTemplateToDto;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = SurveyTemplateController.SURVEY_TEMPLATES,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class SurveyTemplateController {

    public static final String SURVEY_TEMPLATES = "/survey/templates";
    SurveyTemplateService surveyTemplateService;

    @GetMapping
    ResponseEntity<List<SurveyTemplateDto>> getAll() {
        List<SurveyTemplateDto> surveyTemplateDtos = surveyTemplateService.findAll()
                .stream()
                .map(DtoAssemblers::fromSurveyTemplateToDto)
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(surveyTemplateDtos);
    }

    @PostMapping
    ResponseEntity<SurveyTemplateDto> create(UriComponentsBuilder builder, @RequestBody SurveyTemplateDto surveyTemplateDto) {
        UriComponentsBuilder path = builder
                .path(SURVEY_TEMPLATES)
                .path("{id}");
        SurveyTemplate surveyTemplate = surveyTemplateService.create(surveyTemplateDto);
        return ResponseEntity
                .created(path.build(surveyTemplate.getId()))
                .body(fromSurveyTemplateToDto(surveyTemplate));
    }

    @PutMapping("/{templateId}")
    ResponseEntity<SurveyTemplateDto> update(@PathVariable String templateId, @RequestBody SurveyTemplateDto surveyTemplateDto) {
        SurveyTemplate surveyTemplate = surveyTemplateService.update(UUID.fromString(templateId), surveyTemplateDto);
        return ResponseEntity
                .ok(fromSurveyTemplateToDto(surveyTemplate));
    }

    @DeleteMapping("/{templateId}")
    ResponseEntity delete(@PathVariable String templateId) {
        surveyTemplateService.delete(UUID.fromString(templateId));
        return ResponseEntity.noContent().build();
    }
}
