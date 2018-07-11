package domain;

import application.rest.controllers.dto.InspirationDetailDto;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

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

    static InspirationDetail createDefault() {
        return InspirationDetail.builder()
                .url(EMPTY)
                .picture(new byte[]{})
                .rating(0)
                .description(EMPTY)
                .comments(new ArrayList<>())
                .build();
    }

    public InspirationDetail update(InspirationDetailDto inspirationDetailDto) {
        return InspirationDetail.builder()
                .description(isNotBlank(inspirationDetailDto.getDescription()) ? inspirationDetailDto.getDescription() : description)
                .picture(inspirationDetailDto.getPicture().length > 0 ? inspirationDetailDto.getPicture() : picture)
                .rating(inspirationDetailDto.getRating() != null ? inspirationDetailDto.getRating() : rating)
                .url(isNotBlank(inspirationDetailDto.getUrl()) ? inspirationDetailDto.getUrl() : url)
                .comments(comments)
                .build();
    }

    public void add(Comment comment) {
        comments.add(comment);
    }
}
