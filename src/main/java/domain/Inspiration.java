package domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Inspiration implements Id<String> {

    String id;
    @JsonProperty(required = true)
    String name;
    InspirationDetail inspirationDetail;
}