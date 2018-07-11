package domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class InspirationDetail {

    @NonNull
    String description;
    @NonNull
    String url;
    @NonNull
    byte[] picture;
    @NonNull
    Integer rating;
    @NonNull
    List<Comment> comments;
}
