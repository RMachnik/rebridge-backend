package domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Project implements Id<String> {

    String id;
    String name;
    List<Inspiration> inspirations;
}
