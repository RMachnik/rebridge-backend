package application;

import application.dto.CurrentUser;
import domain.user.User;

import java.util.Optional;

public interface UserAuthenticationService {

    Optional<String> login(String email, String password);

    Optional<String> register(String email, String password);

    Optional<User> findByToken(String token);

    void logout(CurrentUser user);
}