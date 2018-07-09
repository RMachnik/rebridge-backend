package domain;

import lombok.Value;

@Value
public class Inspiration implements Id<Long> {

    Long id;
    String name;
    InspirationDetail inspirationDetail;

    @Override
    public Long getId() {
        return id;
    }
}
