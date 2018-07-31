package application.service;

import application.dto.CurrentUser;
import application.dto.DtoAssemblers;
import domain.user.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor
public final class InMemoryAuthenticationService implements UserAuthenticationService {

    @NonNull
    UserService userService;

    final Map<String, User> loggedInUsers = new ConcurrentHashMap<>();

    @Override
    public Optional<CurrentUser> login(final String email, final String password) {
        User foundUser = userService.login(email, password);

        String token = UUID.randomUUID().toString();
        loggedInUsers.put(token, foundUser);

        return Optional.of(DtoAssemblers.fromUserToCurrentUser(foundUser, token));
    }

    @Override
    public Optional<CurrentUser> register(String email, String password) {
        userService.createWithRoleArchitect(email, password);
        return login(email, password);
    }

    @Override
    public Optional<CurrentUser> findByToken(final String token) {
        User user = loggedInUsers.get(token);
        return Optional.ofNullable(user)
                .map((possibleUser) -> DtoAssemblers.fromUserToCurrentUser(possibleUser, token));

    }

    @Override
    public void logout(CurrentUser user) {
        loggedInUsers.remove(user.getToken());
    }

    @Override
    public Optional<CurrentUser> check(String token) {
        return Optional.ofNullable(loggedInUsers.get(token)).map(user -> DtoAssemblers.fromUserToCurrentUser(user, token));
    }
}

