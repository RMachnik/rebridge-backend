package infrastructure.cassandra;

import domain.User;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserCrudRepository extends CrudRepository<User, UUID> {

    @Query("Select * from users where username=?0")
    User findByUsername(String username);
}
