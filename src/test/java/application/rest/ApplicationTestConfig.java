package application.rest;

import application.dto.AuthDto;
import application.service.MailService;
import boot.Application;
import domain.survey.QuestionnaireTemplate;
import domain.survey.QuestionnaireTemplateRepository;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.UUID;

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

    @Autowired
    QuestionnaireTemplateRepository questionnaireTemplateRepository;

    @PostConstruct
    void createTestUser() {
        authController.register(new AuthDto("test@email.com", "password"));
        QuestionnaireTemplate questionnaireTemplate = new QuestionnaireTemplate(UUID.fromString("128cb75a-3b8b-4c3f-b0e7-64e9ec10f467"), "test", Arrays.asList("question"));
        questionnaireTemplateRepository.save(questionnaireTemplate);
    }

    @Bean
    MailService mailService() {
        return Mockito.mock(MailService.class);
    }
}
