package domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

@Value
@Builder
public class User implements Id<String> {

    @NonNull
    String id;
    @NonNull
    String username;
    @NonNull
    String password;
    @NonNull
    List<String> projectIds;

    public static User createUser(String username, String password) {
        final String uuid = UUID.randomUUID().toString();
        return User
                .builder()
                .id(uuid)
                .username(username)
                .password(password)
                .projectIds(new ArrayList<>())
                .build();
    }

    public void checkPassword(String provided) {
        if (password.equals(provided)) {
            throw new DomainExceptions.InvalidPassword(String.format("user not found %s", username));
        }
    }

    public void removeProject(String projectId) {
        projectIds.remove(projectId);
    }

    public boolean canUpdateProject(String projectId) {
        projectIds.stream()
                .filter(project -> project.equals(projectId))
                .findAny()
                .orElseThrow(() -> new DomainExceptions.UserActionNotAllowed(format("user %s is not allowed see this project %s", id, projectId)));
        return true;
    }

}
