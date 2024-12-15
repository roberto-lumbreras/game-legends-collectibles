package org.factoriaf5.game_legends_collectibles.controller;

import java.math.BigDecimal;

import org.factoriaf5.game_legends_collectibles.model.Product;
import org.factoriaf5.game_legends_collectibles.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CartControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProductRepository productRepository;
    Product product;

    @BeforeEach
    void setUp(){
        product = productRepository.save(new Product(null, "", "", "", new BigDecimal(0) , 0));
    }
    @Test
    void testAddToCart() throws Exception{
        Cookie cartCookie = mockMvc.perform(MockMvcRequestBuilders.post("/cart/add/{productId}", product.getId()).param("quantity", "1")).andReturn().getResponse().getCookie("cart");
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add/{productId}", product.getId()).param("quantity", "1").cookie(cartCookie)).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void testGetCartView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRemoveFromCart() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/cart/remove/{productId}", product.getId())).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
