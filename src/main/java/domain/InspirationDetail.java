package domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class InspirationDetail {

    String description;
    String url;
    byte[] picture;
    Integer rating;
    List<Comment> comments;
}
