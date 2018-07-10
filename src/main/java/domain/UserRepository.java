package domain;

import io.vavr.control.Try;

import java.util.Optional;

public interface UserRepository {

    Try<User> save(User user);

    Optional<User> find(String id);

    Optional<User> findByUsername(String username);
}
