package infrastructure;

import domain.User;
import domain.UserRepository;
import io.vavr.control.Try;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public Try<User> save(final User user) {
        return Try.of(() -> users.put(user.getId(), user));
    }

    @Override
    public Optional<User> find(final String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        return Optional.ofNullable(
                users
                        .values()
                        .stream()
                        .filter(u -> Objects.equals(username, u.getUsername()))
                        .findFirst().get()
        );
    }
}
