package application;

import application.service.*;
import infrastructure.InMemoryProjectRepository;
import infrastructure.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("application.rest")
public class ApplicationConfig {

    final InMemoryUserRepository userRepository = new InMemoryUserRepository();

    @Bean
    UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    UserAuthenticationService userAuthenticationService() {
        return new InMemoryAuthenticationService(new UserService(userRepository));
    }

    @Bean
    ProjectService projectService(UserService userService) {
        return new ProjectService(userService, new InMemoryProjectRepository());
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
