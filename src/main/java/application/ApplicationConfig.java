package application;

import application.rest.RestConfig;
import application.service.*;
import domain.PictureRepository;
import domain.ProjectRepository;
import domain.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RestConfig.class)
public class ApplicationConfig {


    @Bean
    UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    UserAuthenticationService userAuthenticationService(UserRepository userRepository) {
        return new InMemoryAuthenticationService(new UserService(userRepository));
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
}
