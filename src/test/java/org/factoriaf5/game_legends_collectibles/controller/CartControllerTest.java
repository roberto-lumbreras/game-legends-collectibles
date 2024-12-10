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
public class CartControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    void testAddToCart() {
        
    }

    @Test
    void testGetCartView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRemoveFromCart() {

    }
}
