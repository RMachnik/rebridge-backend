package infrastructure.extended;

import domain.invitation.Invitation;
import domain.invitation.InvitationRepository;
import infrastructure.springData.InvitationCrudRepository;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@AllArgsConstructor
public class ExtendedInvitationRepository implements InvitationRepository {

    InvitationCrudRepository invitationCrudRepository;

    @Override
    public Optional<Invitation> findById(UUID id) {
        return invitationCrudRepository.findById(id);
    }

    @Override
    public Try<Invitation> save(Invitation entity) {
        return Try.of(() -> invitationCrudRepository.save(entity));
    }

    @Override
    public void deleteById(UUID id) {
        invitationCrudRepository.deleteById(id);
    }

    @Override
    public Optional<Invitation> findByToken(String token) {
        return ofNullable(invitationCrudRepository.findByToken(token));
    }
}
