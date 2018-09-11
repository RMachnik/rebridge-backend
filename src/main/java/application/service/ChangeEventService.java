package application.service;

import application.dto.CurrentUser;
import domain.DomainExceptions;
import domain.event.ChangeEvent;
import domain.event.ChangeEventRepository;
import domain.user.User;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

@AllArgsConstructor
public class ChangeEventService {

    ChangeEventRepository changeEventRepository;
    UserService userService;

    public List<ChangeEvent> loadEvents(CurrentUser currentUser) {
        User user = userService.findById(currentUser.getId());
        return user.getEvents(changeEventRepository);
    }

    public void publish(ChangeEvent changeEvent) {
        changeEventRepository.save(changeEvent);
    }

    public ChangeEvent markAsRed(CurrentUser currentUser, String eventId) {
        ChangeEvent changeEvent = changeEventRepository.findById(UUID.fromString(eventId)).orElseThrow(
                () -> new DomainExceptions.MissingEvent(format("There is no event with id %s.", eventId))
        );
        changeEvent.markAsReed(currentUser.getId());
        changeEventRepository.save(changeEvent);
        return changeEvent;
    }
}
