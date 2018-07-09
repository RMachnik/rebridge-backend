package application;

import domain.InspirationRepository;
import domain.ProjectRepository;
import domain.UserRepository;
import infrastructure.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("application.rest.controllers")
public class ApplicationConfig {

    @Bean
    UserRepository userRepository() {
        return new InMemoryUserRepository();
    }

    @Bean
    ProjectRepository projectRepository(){
        return new InMemoryProjectRepository();
    }

    @Bean
    InspirationRepository inspirationRepository(){
        return new InMemoryInspirationRepository();
    }

    @Bean
    UserAuthenticationService userAuthenticationService(UserRepository userRepository) {
        return new InMemoryAuthenticationService(userRepository);
    }
}
