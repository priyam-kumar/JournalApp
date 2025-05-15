package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.respository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled
public class UserRepositoryImplTests {

    @Autowired
    private UserRepository userRepository;

    @Disabled("tested")
    @Test
    void testSaveNewUser(){
//        Assertions.assertNotNull(userRepository.getUserForSA());
    }
}
