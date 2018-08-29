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
    ProjectService projectService(UserService userService, ProjectRepository projectRepository, QuestionnaireTemplateService questionnaireTemplateService) {
        return new ProjectService(userService, projectRepository, questionnaireTemplateService);
    }

    @Bean
    InspirationService inspirationService(ProjectService projectService) {
        return new InspirationService(projectService);
    }

    @Bean
    CommentService commentService(ProjectService projectService) {
        return new CommentService(projectService);
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
    ProjectDetailsService projectDetailsService(UserService userService, ProjectService projectService, QuestionnaireTemplateService questionnaireTemplateService) {
        return new ProjectDetailsService(userService, projectService, questionnaireTemplateService);
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
}
