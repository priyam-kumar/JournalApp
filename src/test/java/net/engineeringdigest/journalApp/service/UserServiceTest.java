package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.respository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;



import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Disabled
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void testGetUserById() {
        User mockUser = new User("ram", "ram");
        Mockito.when(userRepository.findByUsername("ram")).thenReturn(mockUser);

        User result = userService.findByUsername("ram");
        assertEquals("ram", result.getUsername());
    }
}

