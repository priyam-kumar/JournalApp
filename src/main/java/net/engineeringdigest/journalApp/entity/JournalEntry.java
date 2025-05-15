package net.engineeringdigest.journalApp.entity;

import lombok.*;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "journal_entries")   // JournalEntry entry like a document in case of mongodb
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String Content;
    private LocalDateTime date;

}
