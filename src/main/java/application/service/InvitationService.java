package application.service;

import domain.RepositoryExceptions.InvitationRepositoryException;
import domain.invitation.Invitation;
import domain.invitation.InvitationRepository;
import domain.user.EmailAddress;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public class InvitationService {

    String url;
    InvitationRepository invitationRepository;

    public Invitation create(EmailAddress emailAddress) {
        return invitationRepository.save(Invitation.create(emailAddress, url))
                .onFailure((ex) -> new InvitationRepositoryException(String.format("unable to save invitation %s", Invitation.create(emailAddress, url)), ex))
                .get();
    }

    public Invitation resolve(String token) {
        Invitation invitation = invitationRepository.findByToken(token)
                .orElseThrow(() -> new InvitationRepositoryException(format("invitation not fond")));
        invitationRepository.deleteById(invitation.getId());
        return invitation;
    }
}
