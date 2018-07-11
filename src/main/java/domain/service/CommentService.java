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

    public Comment create(String username, String inspirationId, String comment) {
        Inspiration inspiration = getInspiration(inspirationId);

        Comment saved = Comment.builder()
                .id(UUID.randomUUID().toString())
                .author(username)
                .date(LocalDateTime.now().toString())
                .content(comment)
                .build();

        inspiration.getInspirationDetail().getComments().add(saved);

        inspirationRepository.save(inspiration)
                .getOrElseThrow((ex) -> new DomainExceptions.InspirationRepositoryException(format("unable to update inspiration %s", inspirationId), ex));

        return saved;
    }

    private Inspiration getInspiration(String inspirationId) {
        return inspirationRepository.findById(inspirationId)
                .orElseThrow(() -> new DomainExceptions.InspirationRepositoryException(format("unable to find inspiration %s", inspirationId)));
    }

    public void remove(String inspirationId, String commentId) {
        Inspiration inspiration = getInspiration(inspirationId);
        inspiration.getInspirationDetail().getComments()
                .stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findAny()
                .orElseThrow(() -> new DomainExceptions.InspirationRepositoryException(format("missing comment with id %s in inspiration %s", commentId)));
    }
}
