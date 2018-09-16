package application;

import application.service.*;
import domain.event.ChangeEventRepository;
import domain.invitation.InvitationRepository;
import domain.project.ImageRepository;
import domain.project.ProjectRepository;
import domain.survey.QuestionnaireTemplateRepository;
import domain.user.UserRepository;
import infrastructure.memory.InMemoryLoggedInRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RestConfig.class, MailConfig.class})
public class ServicesConfig {


    @Bean
    UserService userService(UserRepository userRepository, MailService mailService, InvitationService invitationService) {
        return new UserService(userRepository, mailService, invitationService);
    }

    @Bean
    UserAuthenticationService userAuthenticationService(UserService userService) {
        return new AuthenticationService(userService, new InMemoryLoggedInRepository());
    }

    @Bean
    ProjectService projectService(UserService userService,
                                  ProjectRepository projectRepository,
                                  QuestionnaireTemplateService questionnaireTemplateService,
                                  ChangeEventService changeEventService
    ) {
        SimpleProjectService simpleProjectService = new SimpleProjectService(userService, projectRepository, questionnaireTemplateService);
        return new EventableProjectService(simpleProjectService, changeEventService);
    }

    @Bean
    InspirationService inspirationService(ProjectService projectService, ImageService imageService) {
        return new InspirationService(projectService, imageService);
    }

    @Bean
    CommentService commentService(ProjectService projectService, ChangeEventService changeEventService) {
        CommentService simpleCommentService = new SimpleCommentService(projectService);
        return new EventableCommentService(simpleCommentService, changeEventService);
    }

    @Bean
    ImageService pictureService(ProjectService projectService, ImageRepository imageRepository) {
        return new ImageService(projectService, imageRepository);
    }

    @Bean
    QuestionnaireTemplateService surveyTemplateService(QuestionnaireTemplateRepository questionnaireTemplateRepository) {
        return new QuestionnaireTemplateService(questionnaireTemplateRepository);
    }

    @Bean
    ProjectDetailsService projectDetailsService(UserService userService,
                                                ProjectService projectService,
                                                QuestionnaireTemplateService questionnaireTemplateService,
                                                ChangeEventService changeEventService
    ) {
        SimpleProjectDetailsService simpleProjectDetailsService = new SimpleProjectDetailsService(userService, projectService, questionnaireTemplateService);
        return new EventableProjectDetailsService(simpleProjectDetailsService, changeEventService);
    }

    @Bean
    QuestionnaireService surveyService(ProjectService projectService) {
        return new QuestionnaireService(projectService);
    }

    @Bean
    InvitationService invitationService(@Value("${rebridge.frontend.url}") String url, InvitationRepository invitationRepository) {
        return new InvitationService(url, invitationRepository);
    }

    @Bean
    ChangeEventService changeEventService(ChangeEventRepository changeEventRepository, UserService userService) {
        return new ChangeEventService(changeEventRepository, userService);
    }

    @Bean
    ChatService chatService(ProjectService projectService, ChangeEventService changeEventService) {
        SimpleChatService simpleChatService = new SimpleChatService(projectService);
        return new EventableChatService(simpleChatService, changeEventService);
    }

    @Bean
    DocumentationService documentationService(
            UserService userService,
            ImageService imageService,
            ProjectService projectService,
            ChangeEventService changeEventService
    ) {
        SimpleDocumentationService simpleDocumentationService = new SimpleDocumentationService(userService, imageService, projectService);
        return new EventableDocumentationService(simpleDocumentationService, changeEventService);
    }
}
