package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.respository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    //Business logic in service
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User entry){
        entry.setPassword(passwordEncoder.encode(entry.getPassword()));
        entry.setRoles(Arrays.asList("USER"));
        userRepository.save(entry);
    }

    public void saveAdmin(User entry){
        entry.setPassword(passwordEncoder.encode(entry.getPassword()));
        entry.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(entry);
    }
    public void saveUser(User entry){
        userRepository.save(entry);
    }

    public List<User> getAll(){
       return userRepository.findAll();
    }

    public Optional<User> getById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUsername(String user){
        return userRepository.findByUsername(user);
    }


}

//controller  ---> service ---> repository
