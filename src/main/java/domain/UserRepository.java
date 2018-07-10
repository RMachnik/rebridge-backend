package domain;

import java.util.Optional;

public interface UserRepository extends DomainRepository<User, String> {

    Optional<User> findByUsername(String username);
}
