package domain;

import io.vavr.control.Try;

import java.util.List;

public interface CommentRepository {

    List<Comment> findById(String id);

    Try<Comment> save(Comment comment);

    void delete(String id);

}
