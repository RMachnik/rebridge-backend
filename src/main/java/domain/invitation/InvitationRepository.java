package domain.invitation;

import domain.project.DomainRepository;

import java.util.Optional;

public interface InvitationRepository extends DomainRepository<Invitation> {

    Optional<Invitation> findByToken(String token);
}
