package infrastructure.memory;

import domain.user.LoggedInRepository;
import domain.user.User;
import net.jodah.expiringmap.ExpiringMap;

import java.util.Map;

import static java.util.concurrent.TimeUnit.MINUTES;
import static net.jodah.expiringmap.ExpirationPolicy.ACCESSED;

public class InMemoryLoggedInRepository implements LoggedInRepository {

    Map<String, User> map = ExpiringMap.builder()
            .maxSize(123)
            .expiration(15, MINUTES)
            .expirationPolicy(ACCESSED)
            .build();

    @Override
    public User get(String token) {
        return map.get(token);
    }

    @Override
    public void put(String token, User user) {
        map.put(token, user);
    }

    @Override
    public void remove(String token) {
        map.remove(token);
    }
}
