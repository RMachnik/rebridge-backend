package domain.project;

import lombok.Value;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

@UserDefinedType
@Value
public class Document implements WithId<UUID> {

    UUID id;
    String name;
    String mimeType;
    ByteBuffer byteBuffer;

    public static Document create(MultipartFile uploadedFile) throws IOException {
        return new Document(UUID.randomUUID(), uploadedFile.getName(), uploadedFile.getContentType(), ByteBuffer.wrap(uploadedFile.getBytes()));
    }
}
