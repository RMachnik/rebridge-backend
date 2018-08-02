package boot;

import application.ApplicationServicesConfig;
import application.dto.QuestionnaireTemplateDto;
import application.service.QuestionnaireTemplateServices;
import infrastructure.cassandra.CassandraConfig;
import infrastructure.extended.ExtendedRepositoriesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import security.SecurityConfig;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
@Import({CassandraConfig.class, ExtendedRepositoriesConfig.class, ApplicationServicesConfig.class, SecurityConfig.class})
public class Application {

    @Autowired
    QuestionnaireTemplateServices questionnaireTemplateServices;

    @PostConstruct
    void loadTemplates() {
        questionnaireTemplateServices.create(
                new QuestionnaireTemplateDto(null, "initial template", Arrays.asList("question1", "question2"))
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}