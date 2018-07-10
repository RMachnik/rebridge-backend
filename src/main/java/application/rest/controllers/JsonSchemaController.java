package application.rest.controllers;

import application.rest.controllers.dto.InspirationDto;
import application.rest.controllers.dto.ProjectDto;
import application.rest.controllers.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/schemas/")
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class JsonSchemaController {

    ObjectMapper objectMapper;
    JsonSchemaGenerator schemaGenerator;

    JsonSchemaController() {
        objectMapper = new ObjectMapper();

        schemaGenerator = new JsonSchemaGenerator(objectMapper);
    }

    @GetMapping("projects")
    String projects() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
        JsonSchema jsonSchema = schemaGen.generateSchema(ProjectDto.class);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
    }

    @GetMapping("projects/{projectId}/inspirations")
    String inspirations() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
        JsonSchema jsonSchema = schemaGen.generateSchema(InspirationDto.class);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
    }


    @GetMapping("users")
    String users() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
        JsonSchema jsonSchema = schemaGen.generateSchema(UserDto.class);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
    }

}
