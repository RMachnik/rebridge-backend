package application;

import application.rest.RestConfig;
import application.service.*;
import domain.ProjectRepository;
import domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RestConfig.class)
public class ApplicationConfig {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;

    @Bean
    UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    UserAuthenticationService userAuthenticationService() {
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
}
