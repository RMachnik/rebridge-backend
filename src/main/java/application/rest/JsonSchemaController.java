package application.rest;

import application.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = "/schemas/",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class JsonSchemaController {

    ObjectMapper objectMapper;
    JsonSchemaGenerator schemaGenerator;

    JsonSchemaController() {
        objectMapper = new ObjectMapper();

        schemaGenerator = new JsonSchemaGenerator(objectMapper);
    }

    @GetMapping("error")
    ResponseEntity<String> apiError() {
        return generateSchema(RestExceptionHandler.ApiError.class);
    }

    @GetMapping("auth")
    ResponseEntity<String> auth() {
        return generateSchema(AuthDto.class);
    }

    @GetMapping("users/current")
    ResponseEntity<String> users() {
        return generateSchema(CurrentUser.class);
    }

    @GetMapping("projects")
    ResponseEntity<String> projects() {
        return generateSchema(ProjectDto.class);
    }

    @GetMapping("projects/createWithRoleArchitect")
    ResponseEntity<String> createProject() {
        return generateSchema(CreateProjectDto.class);
    }

    @GetMapping("projects/details")
    ResponseEntity<String> projectDetails() {
        return generateSchema(ProjectDetailsDto.class);
    }

    @GetMapping("projects/details/updateOrCreateDetails")
    ResponseEntity<String> createOrUpdateProjectDetails() {
        return generateSchema(UpdateProjectDetailsDto.class);
    }

    @GetMapping("projects/details/questionnaire")
    ResponseEntity<String> questionnaire() {
        return generateSchema(QuestionnaireDto.class);
    }

    @GetMapping("projects/inspirations")
    ResponseEntity<String> inspirations() {
        return generateSchema(InspirationDto.class);
    }

    @GetMapping("projects/inspirations/createOrUpdate")
    ResponseEntity<String> createOrUpdateInspirations() {
        return generateSchema(CreateOrUpdateInspirationDto.class);
    }

    @GetMapping("projects/inspirations/comments")
    ResponseEntity<String> comments() {
        return generateSchema(CommentDto.class);
    }

    @GetMapping("projects/inspirations/comments/createOrUpdate")
    ResponseEntity<String> createOrUpdateComment() {
        return generateSchema(CreateOrUpdateDto.class);
    }

    private ResponseEntity generateSchema(Class clazz) {
        JsonSchema jsonSchema = null;
        try {
            jsonSchema = schemaGenerator.generateSchema(clazz);
            return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(format("Unable to generate schema %s", clazz.getName()));
        }
    }
}
