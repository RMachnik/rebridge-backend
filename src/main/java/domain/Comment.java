package domain;

import lombok.Value;

@Value
public class Comment {

    String author;
    String content;
    String date;
}
