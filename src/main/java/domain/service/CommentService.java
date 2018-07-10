package domain.service;

import domain.*;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Collections.EMPTY_LIST;

@Value
public class CommentService {

    InspirationRepository inspirationRepository;
    CommentRepository commentRepository;

    public List<Comment> getAllByInspirationId(String inspirationId) {
        return inspirationRepository.findById(inspirationId)
                .map(Inspiration::getInspirationDetail)
                .map(InspirationDetail::getComments)
                .orElse(EMPTY_LIST);
    }

    public String create(String username, String inspirationId, String comment) {
        inspirationRepository.findById(inspirationId)
                .orElseThrow(() -> new DomainExceptions.MissingInspirationException(inspirationId));

        Comment saved = commentRepository.save(
                Comment.builder()
                        .id(UUID.randomUUID().toString())
                        .author(username)
                        .date(LocalDateTime.now().toString())
                        .inspirationId(inspirationId)
                        .content(comment)
                        .build()
        ).getOrElseThrow(ex -> new DomainExceptions.UnableToCreateComment(format("unable to create comment for %s", username), ex));
        return saved.getId();
    }
}
