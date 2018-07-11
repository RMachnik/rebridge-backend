package domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Project implements Id<String> {

    @NonNull
    String id;
    @NonNull
    String name;
    @NonNull
    List<Inspiration> inspirations;
}
