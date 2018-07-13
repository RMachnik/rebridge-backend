package domain;

import com.datastax.driver.core.DataType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

@Table("user")
@Data
@Builder
public class User implements Id<UUID>, Serializable {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    @NonNull
    UUID id;

    @Indexed
    @NonNull
    String username;

    @NonNull
    String password;

    @NonNull
    List<UUID> projectIds;

    public User(UUID id, @Indexed String username, String password, List<UUID> projectIds) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.projectIds = projectIds != null ? projectIds : new ArrayList<>();
    }

    public static User createUser(String username, String password) {
        return User
                .builder()
                .id(UUID.randomUUID())
                .username(username)
                .password(password)
                .projectIds(new ArrayList<>())
                .build();
    }

    public void checkPassword(String provided) {
        if (!password.equals(provided)) {
            throw new DomainExceptions.InvalidPassword(String.format("password doesn't match %s", username));
        }
    }

    public void removeProject(String projectId) {
        projectIds.remove(projectId);
    }

    public void canUpdateProject(UUID projectId) {
        projectIds.stream()
                .filter(project -> project.equals(projectId))
                .findAny()
                .orElseThrow(() -> new DomainExceptions.UserActionNotAllowed(format("user %s is not allowed see this project %s", id, projectId)));
    }

}
