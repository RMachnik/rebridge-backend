package infrastructure.extended;

import domain.project.PictureRepository;
import domain.project.ProjectRepository;
import domain.survey.SurveyTemplateRepository;
import domain.user.UserRepository;
import infrastructure.springData.PictureCrudRepository;
import infrastructure.springData.ProjectCrudRepository;
import infrastructure.springData.SurveyTemplateCrudRepository;
import infrastructure.springData.UserCrudRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtendedRepositoriesConfig {

    @Bean
    UserRepository cassandraUserRepository(UserCrudRepository userCrudRepository) {
        return new ExtendedUserRepository(userCrudRepository);
    }

    @Bean
    ProjectRepository cassandraProjectRepository(ProjectCrudRepository projectCrudRepository) {
        return new ExtendedProjectRepository(projectCrudRepository);
    }

    @Bean
    PictureRepository cassandraPictureRepository(PictureCrudRepository pictureCrudRepository) {
        return new ExtendedPictureRepository(pictureCrudRepository);
    }

    @Bean
    SurveyTemplateRepository surveyTemplateRepository(SurveyTemplateCrudRepository surveyTemplateCrudRepository) {
        return new ExtendedSurveyTemplateRepository(surveyTemplateCrudRepository);
    }
}
