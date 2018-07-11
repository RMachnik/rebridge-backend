package domain;

import domain.service.DomainExceptions;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

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

    public void removeProject(String projectId) {
        projectIds.remove(projectId);
    }

    public User checkUser(String projectId) {
        if (canUpdate(projectId)) {
            throw new DomainExceptions.UserActionNotAllowed(format("user %s is not allowed to update this project %s", id, projectId));
        }
        return this;
    }

    private boolean canUpdate(String projectId) {
        return projectIds.stream()
                .filter(project -> project.equals(projectId))
                .count() > 0;
    }
}
