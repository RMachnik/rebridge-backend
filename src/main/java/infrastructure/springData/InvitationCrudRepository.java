package infrastructure.springData;

import domain.invitation.Invitation;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface InvitationCrudRepository extends CrudRepository<Invitation, UUID> {

    @Query("Select * from invitations where token=?0")
    Invitation findByToken(String token);
}
