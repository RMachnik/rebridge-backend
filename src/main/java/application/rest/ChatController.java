package application.rest;

import application.dto.*;
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
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(
        path = "/projects/{projectId}/chat",
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
        return ok(
                ChatDto.create(
                        projectService.findByUserIdAndProjectId(currentUser.getId(), projectId).getChat()
                )
        );
    }

    @GetMapping("/v1/")
    ResponseEntity<ChatV1> getV1(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable String projectId) {
        return ok(
                ChatV1.create(
                        projectService.findByUserIdAndProjectId(currentUser.getId(), projectId).getChat()
                )
        );
    }

    @PostMapping
    ResponseEntity<MessageDto> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable String projectId,
            @RequestBody CreateMessageDto messageDto
    ) {
        Message message = chatService.postMessage(currentUser, projectId, messageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                MessageDto.create(message)
        );
    }
}
