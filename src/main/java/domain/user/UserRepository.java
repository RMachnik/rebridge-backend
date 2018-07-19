package domain.user;

import domain.project.DomainRepository;

import java.util.Optional;

public interface UserRepository extends DomainRepository<User> {

    Optional<User> findByEmail(String email);
}
