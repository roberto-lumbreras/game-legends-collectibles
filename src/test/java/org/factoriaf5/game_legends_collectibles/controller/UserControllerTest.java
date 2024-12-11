package org.factoriaf5.game_legends_collectibles.controller;

import org.factoriaf5.game_legends_collectibles.dto.UserDTO;
import org.factoriaf5.game_legends_collectibles.model.User;
import org.factoriaf5.game_legends_collectibles.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    User user;

    @BeforeEach
    void setUp(){
        user = userRepository.save(new User(null, "user", "", "", "email@mail.com", passwordEncoder.encode("password"), User.Role.ROLE_USER));
    }

    @Test
    void testGetProfileView() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/profile").with(SecurityMockMvcRequestPostProcessors.user("user").password("password").roles("USER")))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateProfile() throws Exception {
        UserDTO userDTO = new UserDTO(null, "updated-email@mail.com", "updated-address", "updated-phone-number");
        String content = new ObjectMapper().writeValueAsString(userDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/profile")
        .content(content)
        .contentType(MediaType.APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.user("user").password("password").roles("USER")))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        user.setEmail(userDTO.email());
        user.setAddress(userDTO.address());
        user.setPhoneNumber(userDTO.phoneNumber());
        assertEquals(user, userRepository.findById(user.getId()).get());
    }
}
