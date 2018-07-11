package domain.service;

import domain.User;
import domain.UserRepository;
import domain.service.DomainExceptions.UserRepositoryException;
import lombok.Value;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Value
public class UserService {

    UserRepository userRepository;

    public User create(String username, String password) {
        final String uuid = UUID.randomUUID().toString();
        final User user = User
                .builder()
                .id(uuid)
                .username(username)
                .password(password)
                .projects(new ArrayList<>())
                .build();

        return userRepository.save(user)
                .getOrElseThrow(
                        ex -> new UserRepositoryException(String.format("problem with adding %s", username, ex))
                );
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
