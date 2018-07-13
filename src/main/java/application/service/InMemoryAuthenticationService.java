package application.service;

import application.UserAuthenticationService;
import application.dto.CurrentUser;
import domain.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class InMemoryAuthenticationService implements UserAuthenticationService {

    @NonNull
    UserService userService;
    final Map<String, User> loggedInUsers = new ConcurrentHashMap<>();

    @Override
    public Optional<String> login(final String username, final String password) {
        User foundUser = userService.findByUsername(username);
        foundUser.checkPassword(password);

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
    public void logout(CurrentUser user) {
        loggedInUsers.remove(user.getId());
    }
}

