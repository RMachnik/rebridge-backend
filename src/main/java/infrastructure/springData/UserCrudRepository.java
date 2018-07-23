package infrastructure.springData;

import domain.user.User;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserCrudRepository extends CrudRepository<User, UUID> {

    @Query("Select * from users where email=?0")
    User findByEmail(String email);
}
