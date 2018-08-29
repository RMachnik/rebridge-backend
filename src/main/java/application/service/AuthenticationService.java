package application.service;

import application.dto.CurrentUser;
import domain.user.LoggedInRepository;
import domain.user.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.Optional;
import java.util.UUID;

import static application.dto.CurrentUser.create;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor
public final class AuthenticationService implements UserAuthenticationService {

    @NonNull
    UserService userService;
    LoggedInRepository loggedInUsers;

    @Override
    public Optional<CurrentUser> login(final String email, final String password) {
        User foundUser = userService.login(email, password);

        String token = UUID.randomUUID().toString();
        loggedInUsers.put(token, foundUser);

        return of(create(foundUser, token));
    }

    @Override
    public Optional<CurrentUser> register(String email, String password) {
        userService.createWithRoleArchitect(email, password);
        return login(email, password);
    }

    @Override
    public Optional<CurrentUser> findByToken(final String token) {
        User user = loggedInUsers.get(token);
        return ofNullable(user)
                .map((possibleUser) -> create(possibleUser, token));

    }

    @Override
    public void logout(CurrentUser user) {
        loggedInUsers.remove(user.getToken());
    }

    @Override
    public Optional<CurrentUser> check(String token) {
        return ofNullable(loggedInUsers.get(token))
                .map(user -> create(user, token));
    }
}

