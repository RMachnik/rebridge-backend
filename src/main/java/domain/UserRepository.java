package domain;

import java.util.Optional;

public interface UserRepository extends DomainRepository<User> {

    Optional<User> findByUsername(String username);
}
