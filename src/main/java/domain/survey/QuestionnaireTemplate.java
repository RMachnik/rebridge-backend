package domain.survey;

import application.dto.QuestionnaireTemplateDto;
import com.datastax.driver.core.DataType;
import domain.project.WithId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table("surveyTemplates")
@Data
@Builder
@AllArgsConstructor
public class QuestionnaireTemplate implements WithId<UUID>, Serializable {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    @NonNull
    UUID id;

    String name;
    List<String> questions;

    public static QuestionnaireTemplate empty(String name) {
        return QuestionnaireTemplate.builder()
                .id(UUID.randomUUID())
                .name(name)
                .questions(new ArrayList<>())
                .build();
    }

    public static QuestionnaireTemplate create(QuestionnaireTemplateDto dto) {
        return QuestionnaireTemplate.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .questions(dto.getQuestions())
                .build();
    }
}
