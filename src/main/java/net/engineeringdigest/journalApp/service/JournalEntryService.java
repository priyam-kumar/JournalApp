package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.respository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    //Business logic in service
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    public void saveEntry(JournalEntry entry){
        entry.setDate(LocalDateTime.now());
        journalEntryRepository.save(entry);
    }

    //@Transactional annotation is basically used to either commit the transcation if all transcation run properly or
    //rollback if there is any failure in translation.
    //To enable the translation we need to enable transactional by using @EnableTransactionManagement annotation.
    @Transactional
    public void saveEntry(JournalEntry entry, String  username){

            entry.setDate(LocalDateTime.now());
            User user = userService.findByUsername(username);
            JournalEntry savedEntry = journalEntryRepository.save(entry);
            user.getJournalEntries().add(savedEntry);
//            user.setPassword(null);  // At this point of time we got the exception for password should not be null; NPE
            //For make this function works we need to use @Transactional Annotation to run the function smoothly
            userService.saveUser(user);
    }

    public List<JournalEntry> getAll(){
       return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id,String username){
        boolean removed = false;
        try {
            User user = userService.findByUsername(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
            return removed;
        } catch (Exception e) {
//            log.trace("Error occured");
//            log.debug("Debug");
            log.error("Error occured");
            log.info("Info");
            log.warn("Error occured");


            throw new RuntimeException("An error occured while deleting the record...",e);
        }
    }
}

//controller  ---> service ---> repository
