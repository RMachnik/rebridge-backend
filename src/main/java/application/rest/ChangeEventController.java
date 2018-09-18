package application.rest;

import application.dto.ChangeEventDto;
import application.dto.CurrentUser;
import application.service.ChangeEventService;
import domain.event.ChangeEvent;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = "/events",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class ChangeEventController {

    ChangeEventService changeEventService;

    @GetMapping
    ResponseEntity<List<ChangeEventDto>> all(@AuthenticationPrincipal CurrentUser currentUser) {
        List<ChangeEventDto> changeEvents = changeEventService.loadEvents(currentUser)
                .stream()
                .sorted(Comparator.comparing(ChangeEvent::getCreationTime))
                .map(changeEvent -> ChangeEventDto.fromDomain(changeEvent, currentUser.getUUID()))
                .collect(toList());
        return ResponseEntity.ok(changeEvents);
    }

    @PutMapping("/{eventId}/red")
    ResponseEntity<ChangeEventDto> markAsRed(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable String eventId) {
        ChangeEvent marked = changeEventService.markAsRed(currentUser, eventId);
        return ResponseEntity.ok(ChangeEventDto.fromDomain(marked, currentUser.getUUID()));
    }
}
