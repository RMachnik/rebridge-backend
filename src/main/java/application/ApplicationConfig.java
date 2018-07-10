package application;

import domain.service.CommentService;
import domain.service.InspirationService;
import domain.service.ProjectService;
import domain.service.UserService;
import infrastructure.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("application.rest.controllers")
public class ApplicationConfig {

    final InMemoryUserRepository userRepository = new InMemoryUserRepository();
    final InMemoryProjectRepository projectRepository = new InMemoryProjectRepository();
    final InMemoryInspirationRepository inspirationRepository = new InMemoryInspirationRepository();

    @Bean
    UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    ProjectService projectService() {
        return new ProjectService(userRepository, projectRepository);
    }

    @Bean
    InspirationService inspirationService() {
        return new InspirationService(projectRepository, inspirationRepository);
    }

    @Bean
    CommentService commentService() {
        return new CommentService(inspirationRepository, new InMemoryCommentRepository());
    }

    @Bean
    UserAuthenticationService userAuthenticationService() {
        return new InMemoryAuthenticationService(new UserService(userRepository));
    }
}
