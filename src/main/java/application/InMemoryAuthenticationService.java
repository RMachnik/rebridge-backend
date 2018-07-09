package application;

import domain.User;
import domain.UserRepository;
import dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
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
    UserRepository userRepository;
    Map<String, User> loggedInUsers = new ConcurrentHashMap<>();

    @Override
    public Optional<String> login(final String username, final String password) {
        return userRepository.findByUsername(username)
                .filter(user -> Objects.equals(user.getPassword(), password))
                .map((loggedUser) -> {
                    String uuid = UUID.randomUUID().toString();
                    loggedInUsers.put(uuid, loggedUser);
                    return uuid;
                });
    }

    @Override
    public Optional<String> register(String username, String password) {
        final String uuid = UUID.randomUUID().toString();
        final User user = User
                .builder()
                .id(uuid)
                .username(username)
                .password(password)
                .build();

        userRepository.save(user);
        return Optional.of(uuid);
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

