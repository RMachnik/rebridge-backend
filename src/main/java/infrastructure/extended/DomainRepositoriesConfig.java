package infrastructure.extended;

import domain.event.ChangeEventRepository;
import domain.invitation.InvitationRepository;
import domain.project.ImageRepository;
import domain.project.ProjectRepository;
import domain.survey.QuestionnaireTemplateRepository;
import domain.user.UserRepository;
import infrastructure.springData.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainRepositoriesConfig {

    @Bean
    UserRepository cassandraUserRepository(UserCrudRepository userCrudRepository) {
        return new ExtendedUserRepository(userCrudRepository);
    }

    @Bean
    ProjectRepository cassandraProjectRepository(ProjectCrudRepository projectCrudRepository) {
        return new ExtendedProjectRepository(projectCrudRepository);
    }

    @Bean
    ImageRepository cassandraPictureRepository(ImageCrudRepository imageCrudRepository) {
        return new ExtendedImageRepository(imageCrudRepository);
    }

    @Bean
    QuestionnaireTemplateRepository surveyTemplateRepository(QuestionnaireCrudRepository questionnaireCrudRepository) {
        return new ExtendedQuestionnaireTemplateRepository(questionnaireCrudRepository);
    }

    @Bean
    InvitationRepository invitationRepository(InvitationCrudRepository invitationCrudRepository) {
        return new ExtendedInvitationRepository(invitationCrudRepository);
    }

    @Bean
    ChangeEventRepository changeEventRepository(ChangeEventCrudRepository changeEventCrudRepository) {
        return new ExtendedChangeEventRepository(changeEventCrudRepository);
    }
}
