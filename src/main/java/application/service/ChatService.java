package application.service;

import application.dto.CreateMessageDto;
import application.dto.CurrentUser;
import domain.project.Message;
import domain.project.Project;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatService {
    ProjectService projectService;

    public Message postMessage(CurrentUser currentUser, String projectId, CreateMessageDto messageDto) {
        Project project = projectService.findByUserIdAndProjectId(currentUser.getId(), projectId);
        Message message = Message.create(currentUser, messageDto);
        project.getChat().postMessage(message);
        return message;
    }
}
