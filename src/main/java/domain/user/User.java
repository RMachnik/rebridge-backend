package domain.user;

import com.datastax.driver.core.DataType;
import com.google.common.collect.Sets;
import domain.project.DomainExceptions;
import domain.project.Id;
import domain.project.Project;
import domain.project.ProjectRepository;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static domain.user.Roles.ARCHITECT;
import static java.lang.String.format;

@Table("users")
@Value
@Builder
public class User implements Id<UUID>, Serializable {

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
    List<UUID> projectIds;

    @NonNull
    Set<Roles> roles;

    public User(UUID id,
                String email,
                String password,
                ContactDetails contactDetails,
                List<UUID> projectIds,
                Set<Roles> roles) {
        this.id = id;
        this.email = Email.isValid(email);
        this.password = password;
        this.contactDetails = contactDetails;
        this.projectIds = projectIds != null ? projectIds : new ArrayList<>();
        this.roles = roles != null ? roles : Sets.newHashSet();
    }

    public static User createUser(String email, String password) {
        return User
                .builder()
                .id(UUID.randomUUID())
                .email(Email.isValid(email))
                .password(password)
                .projectIds(new ArrayList<>())
                .roles(Sets.newHashSet(ARCHITECT))
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
                .count() > 0;
    }

    public Project createProject(String projectName, ProjectRepository projectRepository) {
        if (!isArchitect()) {
            throw new DomainExceptions.UserActionNotAllowed(format("Only Architects can create projects! %s is not an architect!", email));
        }
        Project project = Project.create(projectName);
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
