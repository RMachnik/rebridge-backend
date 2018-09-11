package domain.user;

import application.dto.CreateProjectDto;
import application.dto.UpdateProfileDto;
import com.datastax.driver.core.DataType;
import domain.DomainExceptions.UserActionNotAllowed;
import domain.event.ChangeEvent;
import domain.event.ChangeEventRepository;
import domain.project.*;
import domain.survey.QuestionnaireTemplate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Table("users")
@Value
@Builder
public class User implements WithId<UUID> {

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
        this.email = EmailAddress.validate(email);
        this.password = password;
        this.contactDetails = contactDetails;
        this.projectIds = ofNullable(projectIds).orElse(new HashSet<>());
        this.roles = roles != null ? roles : newHashSet();
    }

    public static User createUser(String email, String password, Roles role) {
        validate(email, password);

        return User
                .builder()
                .id(UUID.randomUUID())
                .email(EmailAddress.validate(email))
                .password(password)
                .projectIds(new HashSet<>())
                .roles(newHashSet(role))
                .contactDetails(ContactDetails.empty())
                .build();
    }

    private static void validate(String email, String password) {
        checkArgument(isNotBlank(email), "email can't be blank");
        checkArgument(isNotBlank(password), "password can't be blank");
    }

    public boolean isPasswordValid(String provided) {
        return password.equals(provided);
    }

    public void removeProject(UUID projectId) {
        projectIds.remove(projectId);
    }

    public boolean canUpdateProject(UUID projectId) {
        return projectIds.contains(projectId);
    }

    public Project createProject(CreateProjectDto createProjectDto,
                                 QuestionnaireTemplate questionnaireTemplate,
                                 ProjectRepository projectRepository,
                                 DocumentationRepository documentationRepository
    ) {
        if (!isArchitect()) {
            throw new UserActionNotAllowed(format("Only Architects can createWithRoleArchitect projects! %s is not an architect!", email));
        }
        Project project = Project.create(createProjectDto.getName(), questionnaireTemplate);
        Documentation documentation = project.createDocumentation();

        projectRepository.save(project);
        documentationRepository.save(documentation);
        projectIds.add(project.getId());
        return project;
    }

    private boolean isArchitect() {
        return roles.contains(Roles.ARCHITECT);
    }

    public void addProject(UUID projectId) {
        projectIds.add(projectId);
    }

    public User update(UpdateProfileDto updateProfileDto) {
        validate(updateProfileDto.getEmail(), password);

        return User.builder()
                .id(id)
                .roles(roles)
                .password(password)
                .projectIds(projectIds)
                .email(updateProfileDto.getEmail())
                .contactDetails(contactDetails.update(updateProfileDto))
                .build();
    }

    public EmailAddress getEmailAddress() {
        return new EmailAddress(email);
    }

    public List<ChangeEvent> getEvents(ChangeEventRepository changeEventRepository) {
        return changeEventRepository.findAllExcludingOwnForProvidedProjects(id, projectIds);
    }
}
