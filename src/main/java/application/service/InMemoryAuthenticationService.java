package application.service;

import application.UserAuthenticationService;
import application.dto.CurrentUser;
import domain.user.User;
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
    public Optional<String> login(final String email, final String password) {
        User foundUser = userService.login(email, password);

        String uuid = UUID.randomUUID().toString();
        loggedInUsers.put(uuid, foundUser);
        return Optional.of(uuid);
    }

    @Override
    public Optional<String> register(String email, String password) {
        userService.create(email, password);
        return login(email, password);
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

