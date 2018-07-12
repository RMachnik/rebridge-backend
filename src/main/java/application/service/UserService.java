package application.service;

import application.service.RepositoryExceptions.UserRepositoryException;
import domain.User;
import domain.UserRepository;
import lombok.Value;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

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
                .projectIds(new ArrayList<>())
                .build();

        return userRepository.save(user)
                .getOrElseThrow(
                        ex -> new UserRepositoryException(format("problem with adding %s", username, ex))
                );
    }

    public User findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserRepositoryException(format("user with id %s is missing", userId)));
    }

    public User update(User user) {
        return userRepository.save(user)
                .getOrElseThrow(() -> new UserRepositoryException(format("problem with updating user %s", user.getId())));
    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
