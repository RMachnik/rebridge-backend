package application;

import application.rest.controllers.dto.UserDto;
import domain.User;
import domain.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class InMemoryAuthenticationService implements UserAuthenticationService {

    @NonNull
    UserService userService;
    final Map<String, User> loggedInUsers = new ConcurrentHashMap<>();

    @Override
    public Optional<String> login(final String username, final String password) {
        User foundUser = userService.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException(String.format("user not found %s", username)));
        String uuid = UUID.randomUUID().toString();
        loggedInUsers.put(uuid, foundUser);
        return Optional.of(uuid);
    }

    @Override
    public Optional<String> register(String username, String password) {
        userService.create(username, password);
        return login(username, password);
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return Optional.ofNullable(loggedInUsers.get(token));
    }

    @Override
    public void logout(UserDto user) {
        loggedInUsers.remove(user.getId());
    }
}

