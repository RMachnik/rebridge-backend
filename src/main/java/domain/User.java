package domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User implements Id<String> {

    String id;
    String username;
    String password;

}
