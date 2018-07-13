package infrastructure.memory;

import domain.User;
import domain.UserRepository;
import io.vavr.control.Try;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {

    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    @Override
    public Try<User> save(final User user) {
        return Try.of(() -> {
            users.put(user.getId(), user);
            return user;
        });
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users
                .values()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public void deleteById(UUID id) {
        throw new UnsupportedOperationException();
    }
}
