package domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Inspiration implements Id<String> {

    @NonNull
    String id;
    @JsonProperty(required = true)
    String name;
    InspirationDetail inspirationDetail;
}