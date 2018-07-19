package domain.survey;

import application.dto.SurveyTemplateDto;
import com.datastax.driver.core.DataType;
import domain.project.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Table("surveyTemplates")
@Data
@Builder
@AllArgsConstructor
public class SurveyTemplate implements Id<UUID>, Serializable {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    @NonNull
    UUID id;

    List<String> questions;

    public static SurveyTemplate create(SurveyTemplateDto dto) {
        return SurveyTemplate.builder()
                .id(UUID.randomUUID())
                .questions(dto.getQuestions())
                .build();
    }
}
