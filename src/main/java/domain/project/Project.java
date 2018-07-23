package domain.project;

import application.dto.CreateOrUpdateInspirationDto;
import application.dto.CreateUpdateProjectDetailsDto;
import application.dto.ProjectDto;
import com.datastax.driver.core.DataType;
import domain.project.DomainExceptions.MissingInspirationException;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


@Table("projects")
@Data
@Builder
public class Project implements Serializable, WithId<UUID> {

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

    public Project(UUID id, String name, Details details, List<Inspiration> inspirations) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.inspirations = inspirations != null ? inspirations : new ArrayList<>();
    }

    public static Project create(String name) {
        return Project.builder()
                .id(UUID.randomUUID())
                .name(name)
                .inspirations(new ArrayList<>())
                .build();
    }

    public Project update(ProjectDto projectDto) {
        return Project.builder()
                .id(id)
                .name(isNotBlank(projectDto.getName()) ? projectDto.getName() : name)
                .inspirations(inspirations)
                .build();
    }

    public Inspiration addInspiration(CreateOrUpdateInspirationDto inspirationName) {
        Inspiration inspiration = Inspiration.create(inspirationName);

        inspirations.add(inspiration);
        return inspiration;
    }

    public Inspiration updateInspiration(String inspirationId, CreateOrUpdateInspirationDto inspirationDto) {
        Inspiration existingInspiration = findInspiration(UUID.fromString(inspirationId));
        Inspiration updatedInspiration = existingInspiration.update(inspirationDto);

        inspirations.remove(existingInspiration);
        inspirations.add(updatedInspiration);
        return updatedInspiration;
    }

    public void removeInspiration(UUID inspirationId) {
        Inspiration inspiration = findInspiration(inspirationId);
        inspirations.remove(inspiration);
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

    public void createDetails(CreateUpdateProjectDetailsDto projectDetailsDto) {
        details = Details.create(projectDetailsDto);
    }
}