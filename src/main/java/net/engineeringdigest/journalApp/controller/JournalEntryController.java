package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/journalApi")
public class JournalEntryController {

    private Map<String, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAllEntry(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public String createEntry(@RequestBody JournalEntry entry){
//        journalEntries.put(entry.getId(), entry);
        return "Ok";
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalByID(@PathVariable long id){
        return journalEntries.get(id);
    }

    @DeleteMapping("/id/{id}")
    public JournalEntry deleteJournalByID(@PathVariable String id){
        return journalEntries.remove(id);
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateJournalByID(@PathVariable String id, @RequestBody JournalEntry entry){
        return journalEntries.put(id,entry);
    }

}
