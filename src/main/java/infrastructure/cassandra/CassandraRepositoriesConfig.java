package infrastructure.cassandra;

import domain.project.PictureRepository;
import domain.project.ProjectRepository;
import domain.survey.SurveyTemplateRepository;
import domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CassandraRepositoriesConfig {

    @Bean
    UserRepository cassandraUserRepository(UserCrudRepository userCrudRepository) {
        return new CassandraUserRepository(userCrudRepository);
    }

    @Bean
    ProjectRepository cassandraProjectRepository(ProjectCrudRepository projectCrudRepository) {
        return new CassandraProjectRepository(projectCrudRepository);
    }

    @Bean
    PictureRepository cassandraPictureRepository(PictureCrudRepository pictureCrudRepository) {
        return new CassandraPictureRepository(pictureCrudRepository);
    }

    @Bean
    SurveyTemplateRepository surveyTemplateRepository(SurveyTemplateCrudRepository surveyTemplateCrudRepository) {
        return new CassandraSurveyTemplateRepository(surveyTemplateCrudRepository);
    }
}
