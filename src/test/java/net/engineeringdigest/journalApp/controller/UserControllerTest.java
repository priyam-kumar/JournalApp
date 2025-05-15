package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Disabled
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetUser() throws Exception {
        Mockito.when(userService.findByUsername("ram"))
                .thenReturn((net.engineeringdigest.journalApp.entity.User) User.builder().username("ram").password("ram").build());

        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ram"));
    }
}

