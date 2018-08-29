package application.rest;

import application.dto.ChatDto;
import application.dto.CreateMessageDto;
import application.dto.CurrentUser;
import application.dto.MessageDto;
import application.service.ChatService;
import application.service.ProjectService;
import domain.project.Message;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping(
        path = "/project/{projectId}/chat",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class ChatController {

    ProjectService projectService;
    ChatService chatService;

    @GetMapping
    ResponseEntity<ChatDto> get(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable String projectId) {
        return ResponseEntity.ok(
                ChatDto.create(
                        projectService.findByUserIdAndProjectId(currentUser.getId(), projectId).getChat()
                )
        );
    }

    @PostMapping
    ResponseEntity<MessageDto> create(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable String projectId, CreateMessageDto messageDto) {
        Message message = chatService.postMessage(currentUser, projectId, messageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                MessageDto.create(message)
        );
    }
}
