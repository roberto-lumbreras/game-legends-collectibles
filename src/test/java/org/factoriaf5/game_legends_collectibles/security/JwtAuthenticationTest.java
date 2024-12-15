package org.factoriaf5.game_legends_collectibles.security;

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

import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JwtAuthenticationTest {
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
    void shouldAccessEndpointWithCustomJwtFilter() throws Exception{
        Cookie tokenCookie = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login").param("username", user.getUsername()).param("password", originalPassword)).andReturn().getResponse().getCookie("token");
        mockMvc.perform(MockMvcRequestBuilders.get("/profile").cookie(tokenCookie)).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
