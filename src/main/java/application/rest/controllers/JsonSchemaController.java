package application.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import domain.Project;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/schemas")
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class JsonSchemaController {

    ObjectMapper objectMapper;
    JsonSchemaGenerator schemaGenerator;

    JsonSchemaController() {
        objectMapper = new ObjectMapper();

        schemaGenerator = new JsonSchemaGenerator(objectMapper);
    }

    @GetMapping("/project")
    String project() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
        JsonSchema jsonSchema = schemaGen.generateSchema(Project.class);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
    }

}
