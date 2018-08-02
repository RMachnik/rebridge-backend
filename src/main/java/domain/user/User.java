package domain.user;

import application.dto.CreateProjectDto;
import com.datastax.driver.core.DataType;
import com.google.common.collect.Sets;
import domain.project.DomainExceptions.UserActionNotAllowed;
import domain.project.Project;
import domain.project.ProjectRepository;
import domain.project.WithId;
import domain.survey.QuestionnaireTemplate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.lang.String.format;

@Table("users")
@Value
@Builder
public class User implements WithId<UUID>, Serializable {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    @NonNull
    UUID id;

    @Indexed
    @NonNull
    String email;

    @NonNull
    String password;

    ContactDetails contactDetails;

    @NonNull
    Set<UUID> projectIds;

    @NonNull
    Set<Roles> roles;

    public User(UUID id,
                String email,
                String password,
                ContactDetails contactDetails,
                Set<UUID> projectIds,
                Set<Roles> roles) {
        this.id = id;
        this.email = EmailAddress.isValid(email);
        this.password = password;
        this.contactDetails = contactDetails;
        this.projectIds = projectIds != null ? new HashSet<>(projectIds) : new HashSet<>();
        this.roles = roles != null ? roles : Sets.newHashSet();
    }

    public static User createUser(String email, String password, Roles role) {
        return User
                .builder()
                .id(UUID.randomUUID())
                .email(EmailAddress.isValid(email))
                .password(password)
                .projectIds(new HashSet<>())
                .roles(Sets.newHashSet(role))
                .contactDetails(ContactDetails.empty())
                .build();
    }

    public boolean isPasswordValid(String provided) {
        return password.equals(provided);
    }

    public void removeProject(UUID projectId) {
        projectIds.remove(projectId);
    }

    public boolean canUpdateProject(UUID projectId) {
        return projectIds.stream()
                .filter(project -> project.equals(projectId))
                .findAny()
                .isPresent();
    }

    public Project createProject(CreateProjectDto createProjectDto, QuestionnaireTemplate questionnaireTemplate, ProjectRepository projectRepository) {
        if (!isArchitect()) {
            throw new UserActionNotAllowed(format("Only Architects can createWithRoleArchitect projects! %s is not an architect!", email));
        }
        Project project = Project.create(createProjectDto.getName(), questionnaireTemplate);
        projectRepository.save(project);
        projectIds.add(project.getId());
        return project;
    }

    private boolean isArchitect() {
        return roles.contains(Roles.ARCHITECT);
    }

    public void addProject(UUID projectId) {
        projectIds.add(projectId);
    }
}
