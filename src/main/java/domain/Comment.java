package domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Comment implements Id<String> {

    String id;
    String inspirationId;
    String author;
    String content;
    String date;
}
