package infrastructure.cassandra;

import domain.User;
import domain.UserRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class CassandraUserRepository implements UserRepository {

    UserCrudRepository userCrudRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userCrudRepository.findByUsername(username));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userCrudRepository.findById(id);
    }

    @Override
    public Try<User> save(User entity) {
        return Try.of(() -> userCrudRepository.save(entity));
    }

    @Override
    public void deleteById(UUID id) {
        userCrudRepository.deleteById(id);
    }
}
