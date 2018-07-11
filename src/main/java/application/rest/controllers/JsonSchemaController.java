package application.rest.controllers;

import application.rest.controllers.dto.InspirationDto;
import application.rest.controllers.dto.ProjectDto;
import application.rest.controllers.dto.UserDto;
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

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = "/schemas/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class JsonSchemaController {

    ObjectMapper objectMapper;
    JsonSchemaGenerator schemaGenerator;

    JsonSchemaController() {
        objectMapper = new ObjectMapper();

        schemaGenerator = new JsonSchemaGenerator(objectMapper);
    }

    @GetMapping("projects")
    ResponseEntity<String> projects() throws JsonProcessingException {
        JsonSchema jsonSchema = schemaGenerator.generateSchema(ProjectDto.class);

        return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
    }

    @GetMapping("projects/{projectId}/inspirations")
    ResponseEntity<String> inspirations() throws JsonProcessingException {
        JsonSchema jsonSchema = schemaGenerator.generateSchema(InspirationDto.class);

        return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
    }


    @GetMapping("users")
    ResponseEntity<String> users() throws JsonProcessingException {
        JsonSchema jsonSchema = schemaGenerator.generateSchema(UserDto.class);

        return ResponseEntity.ok(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
    }

}
