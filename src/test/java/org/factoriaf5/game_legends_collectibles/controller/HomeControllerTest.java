package org.factoriaf5.game_legends_collectibles.controller;

import java.math.BigDecimal;

import org.factoriaf5.game_legends_collectibles.model.Product;
import org.factoriaf5.game_legends_collectibles.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class HomeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProductRepository productRepository;
    @Test
    void testGetHomeView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetProductView() throws Exception {
        Product p = new Product(null, "mockName", "mockDescription", "mockUrl", new BigDecimal(0), 0);
        p = productRepository.save(p);
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}",p.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
