package application;

import application.service.*;
import domain.project.PictureRepository;
import domain.project.ProjectRepository;
import domain.survey.QuestionnaireTemplateRepository;
import domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RestConfig.class, MailConfig.class})
public class ApplicationServicesConfig {


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
    QuestionnaireTemplateServices surveyTemplateService(QuestionnaireTemplateRepository questionnaireTemplateRepository) {
        return new QuestionnaireTemplateServices(questionnaireTemplateRepository);
    }

    @Bean
    ProjectDetailsService projectDetailsService(UserService userService, ProjectService projectService, QuestionnaireTemplateServices questionnaireTemplateServices) {
        return new ProjectDetailsService(userService, projectService, questionnaireTemplateServices);
    }

    @Bean
    QuestionnaireService surveyService(ProjectService projectService) {
        return new QuestionnaireService(projectService);
    }
}
