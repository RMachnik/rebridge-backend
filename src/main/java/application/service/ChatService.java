package application.service;

import application.dto.CreateMessageDto;
import application.dto.CurrentUser;
import domain.project.Message;

public interface ChatService {
    Message postMessage(CurrentUser currentUser, String projectId, CreateMessageDto messageDto);
}
