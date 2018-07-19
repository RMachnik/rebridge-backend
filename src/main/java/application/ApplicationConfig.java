package application;

import application.rest.RestConfig;
import application.service.*;
import domain.project.PictureRepository;
import domain.project.ProjectRepository;
import domain.survey.SurveyTemplateRepository;
import domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RestConfig.class, MailConfig.class})
public class ApplicationConfig {


    @Bean
    UserService userService(UserRepository userRepository, MailService mailService) {
        return new UserService(userRepository, mailService);
    }

    @Bean
    UserAuthenticationService userAuthenticationService(UserService userService) {
        return new InMemoryAuthenticationService(userService);
    }

    @Bean
    ProjectService projectService(UserService userService, ProjectRepository projectRepository) {
        return new ProjectService(userService, projectRepository);
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
    SurveyTemplateService surveyTemplateService(SurveyTemplateRepository surveyTemplateRepository) {
        return new SurveyTemplateService(surveyTemplateRepository);
    }

    @Bean
    ProjectDetailsService projectDetailsService(UserService userService, ProjectService projectService) {
        return new ProjectDetailsService(userService, projectService);
    }

    @Bean
    SurveyService surveyService(ProjectService projectService) {
        return new SurveyService(projectService);
    }
}
