package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")   // JournalEntry entry like a doucment in case of mongodb
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)//to set username should be unique.
    //But it will not set automatically, need to enable the spring-data-mongodb-auto-index-creation=true in application properties
    @NonNull
    private String username;
    @NonNull
    private String password;

    @DBRef //MongoDb relationship for creating link betweeen user and JournalEntry
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles;

}
