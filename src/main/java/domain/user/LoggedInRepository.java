package domain.user;

public interface LoggedInRepository {

    User get(String token);

    void put(String token, User user);

    void remove(String token);
}
