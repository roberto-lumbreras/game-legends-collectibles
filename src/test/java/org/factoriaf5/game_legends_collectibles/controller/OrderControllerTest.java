package org.factoriaf5.game_legends_collectibles.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.factoriaf5.game_legends_collectibles.dto.CartItem;
import org.factoriaf5.game_legends_collectibles.model.Order;
import org.factoriaf5.game_legends_collectibles.model.OrderDetail;
import org.factoriaf5.game_legends_collectibles.model.Product;
import org.factoriaf5.game_legends_collectibles.model.User;
import org.factoriaf5.game_legends_collectibles.repository.OrderRepository;
import org.factoriaf5.game_legends_collectibles.repository.ProductRepository;
import org.factoriaf5.game_legends_collectibles.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.Encoders;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    User user;
    Order order;
    Product product;

    @BeforeEach
    void setUp(){
        user = userRepository.save(new User(null, "user", "", "", "email@mail.com", passwordEncoder.encode("password"), User.Role.ROLE_USER));
        product = productRepository.save(new Product(null, "", "", "", new BigDecimal(0) , 0));
        OrderDetail detail = new OrderDetail(null, 0, new BigDecimal(0), product);
        order = orderRepository.save(new Order(null, 1L , LocalDateTime.now(), new BigDecimal(0), List.of(detail), user));
    }
    @Test
    void testGetOrderDetailsView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{order-number}",order.getOrderNumber()).with(SecurityMockMvcRequestPostProcessors.user("user").password("password").roles("USER")))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetOrderSummaryView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/summary").with(SecurityMockMvcRequestPostProcessors.user("user").password("password").roles("USER")))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetOrdersView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders").with(SecurityMockMvcRequestPostProcessors.user("user").password("password").roles("USER")))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testSaveOrder() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/save")
        .cookie(new Cookie("cart",Encoders.BASE64.encode(new ObjectMapper().writeValueAsString(new CartItem(product.getId(), 1, product.getPrice(), product.getName())).getBytes())))
        .with(SecurityMockMvcRequestPostProcessors.user("user").password("password").roles("USER")))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
