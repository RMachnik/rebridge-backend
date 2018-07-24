package boot;

import application.ApplicationServicesConfig;
import application.dto.SurveyTemplateDto;
import application.service.SurveyTemplateService;
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
    SurveyTemplateService surveyTemplateService;

    @PostConstruct
    void loadTemplates() {
        surveyTemplateService.create(
                new SurveyTemplateDto("1", Arrays.asList("question1", "question2"))
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}