package org.factoriaf5.game_legends_collectibles.controller;

import org.factoriaf5.game_legends_collectibles.model.User;
import org.factoriaf5.game_legends_collectibles.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    User user;
    String originalPassword;

    @BeforeEach
    void setup(){
        originalPassword = "password";
        user = userRepository.save(new User(null, "user", "", "", "email@mail.com", passwordEncoder.encode(originalPassword), User.Role.ROLE_USER));
    }
    @Test
    void testGetLoginView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/login"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetRegisterView() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/register"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testLogin() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login").param("username", user.getUsername()).param("password", originalPassword)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void testLogout() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/logout")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void testRegister() throws Exception{
        String username = "new-user";
        String email = "new-email@mail.com";
        String password = "password";
        String address = "new-address";
        String phoneNumber = "new-phone-number";
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
        .param("email", email)
        .param("username", username)
        .param("password", password)
        .param("address", address)
        .param("phoneNumber", phoneNumber)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
