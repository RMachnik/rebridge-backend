package infrastructure.extended;

import domain.event.ChangeEvent;
import domain.event.ChangeEventRepository;
import infrastructure.springData.ChangeEventCrudRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class ExtendedChangeEventRepository implements ChangeEventRepository {

    ChangeEventCrudRepository changeEventCrudRepository;

    @Override
    public Optional<ChangeEvent> findById(UUID id) {
        return changeEventCrudRepository.findById(id);
    }

    @Override
    public Try<ChangeEvent> save(ChangeEvent entity) {
        return Try.of(() -> changeEventCrudRepository.save(entity));
    }

    @Override
    public void deleteById(UUID id) {
        changeEventCrudRepository.deleteById(id);
    }

    @Override
    public List<ChangeEvent> findAllExcludingOwnForProvidedProjects(UUID userId, Collection<UUID> projectIds) {
        return StreamSupport.stream(changeEventCrudRepository.findAll().spliterator(), false)
                .filter(changeEvent -> !changeEvent.getUserId().equals(userId))
                .filter(changeEvent -> projectIds.contains(changeEvent.getProjectId()))
                .collect(toList());
    }
}
