package application.service;

import application.dto.CurrentUser;

import java.util.Optional;

public interface UserAuthenticationService {

    Optional<CurrentUser> login(String email, String password);

    Optional<CurrentUser> register(String email, String password);

    Optional<CurrentUser> findByToken(String token);

    void logout(CurrentUser user);
}