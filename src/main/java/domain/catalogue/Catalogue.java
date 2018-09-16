package domain.catalogue;

import com.google.common.collect.Lists;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@UserDefinedType
@Value
public class Catalogue {

    UUID id;
    List<Room> rooms;


    public Catalogue(UUID id, List<Room> rooms) {
        this.id = id;
        this.rooms = Optional
                .ofNullable(rooms)
                .orElse(Lists.newArrayList());
    }

    public static Catalogue empty() {
        return new Catalogue(UUID.randomUUID(), Lists.newArrayList());
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
