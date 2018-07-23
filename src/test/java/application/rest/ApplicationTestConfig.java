package application.rest;

import application.dto.AuthDto;
import application.service.MailService;
import boot.Application;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

@Configuration
@Import(Application.class)
public class ApplicationTestConfig {

    static {
        try {
            EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    @Autowired
    AuthController authController;

    @PostConstruct
    void createTestUser() {
        authController.register(new AuthDto("test@email.com", "password"));
    }

    @Bean
    MailService mailService() {
        return Mockito.mock(MailService.class);
    }
}
