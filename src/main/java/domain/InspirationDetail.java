package domain;

import application.dto.InspirationDetailDto;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@UserDefinedType
@Data
@Builder
public class InspirationDetail implements Serializable {

    @NonNull
    String description;
    @NonNull
    String url;
    UUID pictureId;
    @NonNull
    Integer rating;
    @NonNull
    List<Comment> comments;


    public InspirationDetail(String description, String url, UUID pictureId, Integer rating, List<Comment> comments) {
        this.description = description;
        this.url = url;
        this.pictureId = pictureId;
        this.rating = rating;
        this.comments = comments != null ? comments : new ArrayList<>();
    }

    static InspirationDetail createDefault() {
        return InspirationDetail.builder()
                .url(EMPTY)
                .pictureId(null)
                .rating(0)
                .description(EMPTY)
                .comments(new ArrayList<>())
                .build();
    }

    public InspirationDetail update(InspirationDetailDto inspirationDetailDto) {
        return InspirationDetail.builder()
                .description(isNotBlank(inspirationDetailDto.getDescription()) ? inspirationDetailDto.getDescription() : description)
                .pictureId(pictureId)
                .rating(inspirationDetailDto.getRating() != null ? inspirationDetailDto.getRating() : rating)
                .url(isNotBlank(inspirationDetailDto.getUrl()) ? inspirationDetailDto.getUrl() : url)
                .comments(comments)
                .build();
    }

    public InspirationDetail updatePictureId(UUID pictureId) {
        this.pictureId = pictureId;
        return this;
    }

    public void add(Comment comment) {
        comments.add(comment);
    }
}
