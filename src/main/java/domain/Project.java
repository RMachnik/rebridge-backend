package domain;

import application.dto.InspirationDto;
import application.dto.ProjectDto;
import domain.DomainExceptions.MissingInspirationException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Value
@Builder
public class Project implements Id<String> {

    @NonNull
    String id;
    @NonNull
    String name;
    @NonNull
    List<Inspiration> inspirations;

    public static Project create(String name) {
        return Project.builder()
                .id(UUID.randomUUID().toString())
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

    public Inspiration addInspiration(String inspirationName) {
        Inspiration inspiration = Inspiration.create(inspirationName);

        inspirations.add(inspiration);
        return inspiration;
    }

    public Inspiration updateInspiration(InspirationDto inspirationDto) {
        Inspiration existingInspiration = findInspiration(inspirationDto.getId());
        Inspiration updatedInspiration = existingInspiration.update(inspirationDto);

        inspirations.remove(existingInspiration);
        inspirations.add(updatedInspiration);
        return updatedInspiration;
    }

    public void removeInspiration(String inspirationId) {
        Inspiration inspiration = findInspiration(inspirationId);
        inspirations.remove(inspiration);
    }

    public Inspiration findInspiration(String inspirationId) {
        return findInspirationById(inspirationId)
                .orElseThrow(() -> new MissingInspirationException(format("missing inspiration %s in project %s", inspirationId, id)));
    }

    private Optional<Inspiration> findInspirationById(String inspirationId) {
        return inspirations.stream()
                .filter(inspiration -> inspiration.getId().equals(inspirationId))
                .findAny();
    }
}
