package application.service;

import application.service.RepositoryExceptions.UserRepositoryException;
import application.service.ServiceExceptions.ServiceException;
import domain.project.DomainExceptions;
import domain.user.User;
import domain.user.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@AllArgsConstructor
public class UserService {

    UserRepository userRepository;

    public User create(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ServiceException(format("email %s already exists, try different name", email));
        }

        User user = User.createUser(email, password);
        return userRepository.save(user)
                .getOrElseThrow(
                        ex -> new UserRepositoryException(format("problem with adding %s", email, ex))
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

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserRepositoryException(format("user %s not found", email)));
    }

    public Optional<User> tryFindUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User login(String email, String password) {
        User user = findByEmail(email);
        if (!user.isPasswordValid(password)) {
            throw new DomainExceptions.InvalidPassword(String.format("password doesn't match %s", email));
        }
        return user;
    }
}
