package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


//    @GetMapping
//    public ResponseEntity<?> getAllEntry() {
//        List<JournalEntry> allEntry = journalEntryService.getAll();
//        if (allEntry != null && !allEntry.isEmpty()) {
//            return new ResponseEntity<>(allEntry, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//    }

//    @PostMapping
//    public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry) {
//        try {
//            entry.setDate(LocalDateTime.now());
//            journalEntryService.saveEntry(entry);
//            return new ResponseEntity<>(entry, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//    }
//    @PutMapping("/id/{id}")
//    public ResponseEntity<JournalEntry> updateJournalByID(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
//        JournalEntry old = journalEntryService.getById(id).orElse(null);
//        if (old != null) {
//            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
//            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
//            journalEntryService.saveEntry(old);
//            return new ResponseEntity<>(old, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @DeleteMapping("/id/{id}")
//    public ResponseEntity<?> deleteJournalByID(@PathVariable ObjectId id) {
//        journalEntryService.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEnterieByUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> allEntry = user.getJournalEntries();
        if (allEntry != null && !allEntry.isEmpty()) {
            return new ResponseEntity<>(allEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createUserEntryWithJournal(@RequestBody JournalEntry entry) {
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(entry, username);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Not Created");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalByID(@PathVariable ObjectId id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.getById(id);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalByUser(@PathVariable ObjectId id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteById(id, username);
        if (removed){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalByforUser(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getById(id);
            if (journalEntry.isPresent()){
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getTitle().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
