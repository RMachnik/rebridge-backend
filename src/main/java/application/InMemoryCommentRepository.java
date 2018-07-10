package application;

import domain.Comment;
import domain.CommentRepository;
import io.vavr.control.Try;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCommentRepository implements CommentRepository {

    final Map<String, Comment> comments = new ConcurrentHashMap<>();

    @Override
    public Optional<Comment> findById(String id) {
        return Optional.ofNullable(comments.get(id));
    }

    @Override
    public Try<Comment> save(Comment entity) {
        return Try.of(() -> comments.put(entity.getId(), entity));
    }

    @Override
    public void delete(String id) {
        comments.remove(id);
    }
}
