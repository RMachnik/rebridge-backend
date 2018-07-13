package infrastructure.memory;

import domain.ProjectRepository;
import domain.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemoryConfig {

    @Bean
    UserRepository inMemoryUserRepository() {
        return new InMemoryUserRepository();
    }

    @Bean
    ProjectRepository inMemoryProjectRepository() {
        return new InMemoryProjectRepository();
    }
}
