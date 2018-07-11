package domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class Comment implements Id<String> {

    @NotNull
    String id;
    @NonNull
    String author;
    @NonNull
    String content;
    @NonNull
    String date;
}
