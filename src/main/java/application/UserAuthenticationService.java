package application;

import application.dto.UserDto;
import domain.User;

import java.util.Optional;

public interface UserAuthenticationService {

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @return an {@link Optional} of a user when login succeeds
     */
    Optional<String> login(String username, String password);

    /**
     * Registers with the given {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @return an {@link Optional} id of user
     */
    Optional<String> register(String username, String password);


    Optional<User> findByToken(String token);

    /**
     * Logs out the given input {@code user}.
     *
     * @param user the user to logout
     */
    void logout(UserDto user);
}