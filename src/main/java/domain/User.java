package domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class User implements Id<String> {

    String id;
    String username;
    String password;
    List<Project> projects;
}
