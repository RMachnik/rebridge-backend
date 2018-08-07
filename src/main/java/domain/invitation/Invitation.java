package domain.invitation;

import com.datastax.driver.core.DataType;
import domain.project.WithId;
import domain.user.EmailAddress;
import lombok.NonNull;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@UserDefinedType("invitations")
@Value
public class Invitation implements WithId<UUID> {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    @NonNull
    UUID id;

    @NonNull
    EmailAddress emailAddress;

    @Indexed
    @NonNull
    UUID token;

    String url;

    public static Invitation create(EmailAddress emailAddress, String url) {
        return new Invitation(
                randomUUID(),
                emailAddress,
                randomUUID(),
                url
        );
    }

    public String getRedirection() {
        return url;
    }
}
