package application;

import application.service.*;
import domain.project.PictureRepository;
import domain.project.ProjectRepository;
import domain.survey.QuestionnaireTemplateRepository;
import domain.user.UserRepository;
import infrastructure.memory.InMemoryLoggedInRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RestConfig.class, MailConfig.class})
public class ServicesConfig {


    @Bean
    UserService userService(UserRepository userRepository, MailService mailService) {
        return new UserService(userRepository, mailService);
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
    PictureService pictureService(ProjectService projectService, PictureRepository pictureRepository) {
        return new PictureService(projectService, pictureRepository);
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
}
