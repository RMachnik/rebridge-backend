package application.rest;

import application.dto.DtoAssemblers;
import application.dto.QuestionnaireTemplateDto;
import application.service.QuestionnaireTemplateServices;
import domain.survey.QuestionnaireTemplate;
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
        path = QuestionnaireTemplateController.QUESTIONNAIRE_TEMPLATES,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class QuestionnaireTemplateController {

    public static final String QUESTIONNAIRE_TEMPLATES = "/questionnaire/templates";
    QuestionnaireTemplateServices questionnaireTemplateServices;

    @GetMapping
    ResponseEntity<List<QuestionnaireTemplateDto>> getAll() {
        List<QuestionnaireTemplateDto> questionnaireTemplateDtos = questionnaireTemplateServices.findAll()
                .stream()
                .map(DtoAssemblers::fromSurveyTemplateToDto)
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(questionnaireTemplateDtos);
    }

    @PostMapping
    ResponseEntity<QuestionnaireTemplateDto> create(UriComponentsBuilder builder, @RequestBody QuestionnaireTemplateDto questionnaireTemplateDto) {
        UriComponentsBuilder path = builder
                .path(QUESTIONNAIRE_TEMPLATES)
                .path("{id}");
        QuestionnaireTemplate questionnaireTemplate = questionnaireTemplateServices.create(questionnaireTemplateDto);
        return ResponseEntity
                .created(path.build(questionnaireTemplate.getId()))
                .body(fromSurveyTemplateToDto(questionnaireTemplate));
    }

    @PutMapping("/{templateId}")
    ResponseEntity<QuestionnaireTemplateDto> update(@PathVariable String templateId, @RequestBody QuestionnaireTemplateDto questionnaireTemplateDto) {
        QuestionnaireTemplate questionnaireTemplate = questionnaireTemplateServices.update(UUID.fromString(templateId), questionnaireTemplateDto);
        return ResponseEntity
                .ok(fromSurveyTemplateToDto(questionnaireTemplate));
    }

    @DeleteMapping("/{templateId}")
    ResponseEntity delete(@PathVariable String templateId) {
        questionnaireTemplateServices.delete(UUID.fromString(templateId));
        return ResponseEntity.noContent().build();
    }
}
