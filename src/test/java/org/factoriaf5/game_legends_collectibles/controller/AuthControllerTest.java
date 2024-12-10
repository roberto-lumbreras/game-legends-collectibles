package org.factoriaf5.game_legends_collectibles.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;
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
    void testLogin() {

    }

    @Test
    void testLogout() {

    }

    @Test
    void testRegister() {

    }
}
