package domain.survey;

import application.dto.QuestionnaireTemplateDto;
import com.datastax.driver.core.DataType;
import domain.common.DateTime;
import domain.project.WithId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Table("surveyTemplates")
@Value
@Builder
public class QuestionnaireTemplate implements WithId<UUID>, Serializable {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    @NonNull
    UUID id;

    String name;
    List<String> questions;
    DateTime creationDate;

    public QuestionnaireTemplate(UUID id, String name, List<String> questions, DateTime creationDate) {
        this.id = id;
        this.name = name;
        this.questions = ofNullable(questions).orElse(new ArrayList<>());
        this.creationDate = creationDate;
    }

    public static QuestionnaireTemplate empty(String name) {
        return QuestionnaireTemplate.builder()
                .id(UUID.randomUUID())
                .name(name)
                .questions(new ArrayList<>())
                .creationDate(DateTime.now())
                .build();
    }

    public static QuestionnaireTemplate create(QuestionnaireTemplateDto dto) {
        return QuestionnaireTemplate.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .questions(dto.getQuestions())
                .creationDate(DateTime.now())
                .build();
    }

    public QuestionnaireTemplate updateQuestions(List<String> updatedQuestions) {
        return new QuestionnaireTemplate(id, name, updatedQuestions, creationDate);
    }
}
