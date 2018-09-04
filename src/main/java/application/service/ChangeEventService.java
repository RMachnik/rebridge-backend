package application.service;

import application.dto.CurrentUser;
import domain.event.ChangeEvent;
import domain.event.ChangeEventRepository;
import domain.user.User;
import lombok.AllArgsConstructor;

import java.util.List;

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
}
