package application.service;

import application.service.RepositoryExceptions.UserRepositoryException;
import application.service.ServiceExceptions.ServiceException;
import domain.User;
import domain.UserRepository;
import lombok.Value;

import java.util.UUID;

import static java.lang.String.format;

@Value
public class UserService {

    UserRepository userRepository;

    public User create(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ServiceException(format("username %s already exists, try different name", username));
        }

        User user = User.createUser(username, password);
        return userRepository.save(user)
                .getOrElseThrow(
                        ex -> new UserRepositoryException(format("problem with adding %s", username, ex))
                );
    }

    public User findById(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserRepositoryException(format("user with id %s is missing", userId)));
    }

    public User update(User user) {
        return userRepository.save(user)
                .getOrElseThrow(() -> new UserRepositoryException(format("problem with updating user %s", user.getId())));
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserRepositoryException(format("user %s not found", username)));
    }
}
