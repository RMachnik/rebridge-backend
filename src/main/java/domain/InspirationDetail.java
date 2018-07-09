package domain;

import lombok.Value;

import java.util.List;

@Value
public class InspirationDetail {

    String description;
    String url;
    byte[] picture;
    Integer rating;
    List<Comment> comments;

}
