package application.dto;

import domain.project.InspirationDetails;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
public class InspirationDetailDto {

    String description;
    String url;
    String imageId;
    Integer rating;
    List<CommentDto> comments;

    static InspirationDetailDto create(InspirationDetails inspirationDetails) {
        return builder()
                .description(inspirationDetails.getDescription())
                .imageId(Optional.ofNullable(inspirationDetails.getImageId()).map(UUID::toString).orElse(null))
                .rating(inspirationDetails.getRating())
                .url(inspirationDetails.getUrl())
                .comments(DtoAssemblers.fromCommentsToDtos(inspirationDetails.getComments()))
                .build();
    }
}
