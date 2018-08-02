package infrastructure.extended;

import domain.project.PictureRepository;
import domain.project.ProjectRepository;
import domain.survey.QuestionnaireTemplateRepository;
import domain.user.UserRepository;
import infrastructure.springData.PictureCrudRepository;
import infrastructure.springData.ProjectCrudRepository;
import infrastructure.springData.QuestionnaireCrudRepository;
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
    QuestionnaireTemplateRepository surveyTemplateRepository(QuestionnaireCrudRepository questionnaireCrudRepository) {
        return new ExtendedQuestionnaireTemplateRepository(questionnaireCrudRepository);
    }
}
