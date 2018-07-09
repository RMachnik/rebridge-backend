package domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Inspiration implements Id<String> {

    String id;
    String name;
    InspirationDetail inspirationDetail;

}