package domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

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
    List<Project> projects;
}
