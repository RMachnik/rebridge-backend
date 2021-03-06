package domain.catalogue;

import com.google.common.collect.Lists;
import domain.DomainExceptions.MissingRoom;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Arrays;
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
        return new Catalogue(
                UUID.randomUUID(),
                Arrays.asList(Room.create("Kuchnia"), Room.create("Łazienka"), Room.create("Korytarz"), Room.create("Sypialnia")));
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room findRoom(String roomId) {
        UUID roomUUID = UUID.fromString(roomId);
        return rooms.stream()
                .filter(room -> room.getId().equals(roomUUID))
                .findFirst()
                .orElseThrow(() -> new MissingRoom(String.format("There is no room with id %s", roomId)));
    }

    public void removeRoom(String roomId) {
        UUID roomUUID = UUID.fromString(roomId);
        Room roomFound = rooms.stream()
                .filter(room -> room.getId().equals(roomUUID))
                .findFirst()
                .orElseThrow(() -> new MissingRoom(String.format("There is no room with id %s", roomId)));
        rooms.remove(roomFound);
    }
}
