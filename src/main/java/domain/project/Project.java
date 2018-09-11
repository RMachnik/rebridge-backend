package domain.project;

import application.dto.CreateOrUpdateInspirationDto;
import application.dto.ProjectDto;
import application.dto.UpdateProjectDetailsDto;
import com.datastax.driver.core.DataType;
import domain.DomainExceptions.MissingInspirationException;
import domain.common.DateTime;
import domain.survey.QuestionnaireTemplate;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.UUID.fromString;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


@Table("projects")
@Data
@Builder
public class Project implements WithId<UUID> {

    @Id
    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    @NonNull
    UUID id;

    @NonNull
    String name;

    @NonNull
    Details details;

    @NonNull
    List<Inspiration> inspirations;

    UUID questionnaireTemplateId;

    Chat chat;

    UUID documentationId;

    DateTime creationDate;

    public Project(UUID id, String name,
                   Details details,
                   List<Inspiration> inspirations,
                   UUID questionnaireTemplateId,
                   Chat chat,
                   UUID documentationId,
                   DateTime creationDate) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.inspirations = ofNullable(inspirations).orElse(new ArrayList<>());
        this.questionnaireTemplateId = questionnaireTemplateId;
        this.chat = chat;
        this.documentationId = documentationId;
        this.creationDate = creationDate;
    }

    public static Project create(String name, QuestionnaireTemplate questionnaireTemplate) {
        checkArgument(isNotBlank(name), "project name can't be empty");

        UUID id = UUID.randomUUID();
        return Project.builder()
                .id(id)
                .name(name)
                .inspirations(new ArrayList<>())
                .details(Details.empty(questionnaireTemplate))
                .questionnaireTemplateId(questionnaireTemplate.getId())
                .chat(Chat.empty(id))
                .creationDate(DateTime.now())
                .build();
    }

    public Project update(ProjectDto projectDto) {
        return Project.builder()
                .id(id)
                .name(isNotBlank(projectDto.getName()) ? projectDto.getName() : name)
                .inspirations(inspirations)
                .questionnaireTemplateId(isNotBlank(projectDto.getQuestionnaireTemplateId()) ? fromString(projectDto.getQuestionnaireTemplateId()) : questionnaireTemplateId)
                .chat(chat)
                .creationDate(creationDate)
                .build();
    }

    public Inspiration addInspiration(CreateOrUpdateInspirationDto inspirationName) {
        Inspiration inspiration = Inspiration.create(inspirationName);

        inspirations.add(inspiration);
        return inspiration;
    }

    public Inspiration updateInspiration(String inspirationId, CreateOrUpdateInspirationDto inspirationDto) {
        Inspiration existingInspiration = findInspiration(fromString(inspirationId));
        Inspiration updatedInspiration = existingInspiration.update(inspirationDto);

        inspirations.remove(existingInspiration);
        inspirations.add(updatedInspiration);
        return updatedInspiration;
    }

    public Inspiration removeInspiration(UUID inspirationId) {
        Inspiration inspiration = findInspiration(inspirationId);
        inspirations.remove(inspiration);
        return inspiration;
    }

    public Inspiration findInspiration(UUID inspirationId) {
        return findInspirationById(inspirationId)
                .orElseThrow(() -> new MissingInspirationException(format("missing inspiration %s in project %s", inspirationId, id)));
    }

    private Optional<Inspiration> findInspirationById(UUID inspirationId) {
        return inspirations.stream()
                .filter(inspiration -> inspiration.getId().equals(inspirationId))
                .findAny();
    }

    public Details createDetails(UpdateProjectDetailsDto projectDetailsDto, Questionnaire questionnaire) {
        details = Details.create(projectDetailsDto, questionnaire);
        return details;
    }

    public Documentation createDocumentation() {
        Documentation documentation = Documentation.empty(id);
        this.documentationId = documentation.getId();
        return documentation;
    }

    public void updateDetails(UpdateProjectDetailsDto updateProjectDetailsDto) {
        this.details = details.update(updateProjectDetailsDto);
    }

    public void updateImage(UUID imageId) {
        this.details = details.updateImage(imageId);
    }
}
