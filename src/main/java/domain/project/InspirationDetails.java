package domain.project;

import application.dto.CreateOrUpdateInspirationDto;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@UserDefinedType
@Value
@Builder
public class InspirationDetails implements Serializable {

    @NonNull
    String description;

    @NonNull
    String url;

    UUID pictureId;

    @NonNull
    Integer rating;

    @NonNull
    List<Comment> comments;


    public InspirationDetails(String description, String url, UUID pictureId, Integer rating, List<Comment> comments) {
        this.description = description;
        this.url = url;
        this.pictureId = pictureId;
        this.rating = rating;
        this.comments = comments != null ? comments : new ArrayList<>();
    }

    static InspirationDetails create(CreateOrUpdateInspirationDto createOrUpdateInspirationDto) {
        return InspirationDetails.builder()
                .url(createOrUpdateInspirationDto.getUrl())
                .pictureId(null)
                .rating(createOrUpdateInspirationDto.getRating())
                .description(createOrUpdateInspirationDto.getDescription())
                .comments(new ArrayList<>())
                .build();
    }

    public InspirationDetails update(CreateOrUpdateInspirationDto inspirationDetailDto) {
        return InspirationDetails.builder()
                .description(isNotBlank(inspirationDetailDto.getDescription()) ? inspirationDetailDto.getDescription() : description)
                .pictureId(pictureId)
                .rating(inspirationDetailDto.getRating() != null ? inspirationDetailDto.getRating() : rating)
                .url(isNotBlank(inspirationDetailDto.getUrl()) ? inspirationDetailDto.getUrl() : url)
                .comments(comments)
                .build();
    }

    public InspirationDetails updatePictureId(UUID pictureId) {
        return new InspirationDetails(description, url, pictureId, rating, comments);
    }

    public void add(Comment comment) {
        comments.add(comment);
    }
}
