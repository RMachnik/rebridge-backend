package boot;

import application.ServicesConfig;
import application.dto.QuestionnaireTemplateDto;
import application.service.QuestionnaireTemplateService;
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
@Import({CassandraConfig.class, ExtendedRepositoriesConfig.class, ServicesConfig.class, SecurityConfig.class})
public class Application {

    @Autowired
    QuestionnaireTemplateService questionnaireTemplateService;

    @PostConstruct
    void loadTemplates() {
        questionnaireTemplateService.create(
                new QuestionnaireTemplateDto(null, "initial template", Arrays.asList("question1", "question2"))
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}