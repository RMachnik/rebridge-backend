package infrastructure.extended;

import domain.user.User;
import domain.user.UserRepository;
import infrastructure.springData.UserCrudRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ExtendedUserRepository implements UserRepository {

    UserCrudRepository userCrudRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userCrudRepository.findByEmail(email));
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
