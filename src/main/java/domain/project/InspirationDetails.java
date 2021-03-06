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

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@UserDefinedType
@Value
@Builder
public class InspirationDetails implements Serializable {

    @NonNull
    String description;

    @NonNull
    String url;

    UUID imageId;

    @NonNull
    Integer rating;

    @NonNull
    List<Comment> comments;


    public InspirationDetails(
            String description,
            String url,
            UUID imageId,
            Integer rating,
            List<Comment> comments) {
        this.description = description;
        this.url = url;
        this.imageId = imageId;
        this.rating = rating;
        this.comments = comments != null ? comments : new ArrayList<>();
    }

    static InspirationDetails create(CreateOrUpdateInspirationDto createOrUpdateInspirationDto) {
        return InspirationDetails.builder()
                .url(createOrUpdateInspirationDto.getUrl())
                .imageId(null)
                .rating(createOrUpdateInspirationDto.getRating())
                .description(createOrUpdateInspirationDto.getDescription())
                .comments(new ArrayList<>())
                .build();
    }

    public InspirationDetails update(CreateOrUpdateInspirationDto inspirationDetailDto) {
        return InspirationDetails.builder()
                .description(isNotBlank(inspirationDetailDto.getDescription()) ? inspirationDetailDto.getDescription() : description)
                .imageId(imageId)
                .rating(ofNullable(inspirationDetailDto.getRating()).orElse(rating))
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
