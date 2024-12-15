package org.factoriaf5.game_legends_collectibles.controller;

import java.math.BigDecimal;

import org.factoriaf5.game_legends_collectibles.dto.ProductDTO;
import org.factoriaf5.game_legends_collectibles.model.Product;
import org.factoriaf5.game_legends_collectibles.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProductRepository productRepository;
    Product product;

    @BeforeEach
    void setUp(){
        product = productRepository.save(new Product(null, "", "", "", new BigDecimal(0) , 0));
        ProductDTO dto = new ProductDTO(product);
    }

    @Test
    void testCreateProduct() throws Exception{
        MockMultipartFile file = new MockMultipartFile("img", "img.jpg", null, "file-content".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/products/create")
        .file(file)
        .param("name", "updated-name")
        .param("description", "updated-description")
        .param("stock", "10")
        .param("price", "10.0")
        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("password").roles("ADMIN")))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void testDeleteProduct() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/products/delete/{id}",product.getId()).with(SecurityMockMvcRequestPostProcessors.user("admin").password("password").roles("ADMIN")))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void testEditProduct() throws Exception{
        MockMultipartFile file = new MockMultipartFile("img", "img.jpg", null, "file-content".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/products/edit/{id}",product.getId())
        .file(file)
        .param("name", "updated-name")
        .param("description", "updated-description")
        .param("stock", "10")
        .param("price", "10.0")
        .with(SecurityMockMvcRequestPostProcessors.user("admin").password("password").roles("ADMIN")))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void testGetCreateProductView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/products/create").with(SecurityMockMvcRequestPostProcessors.user("admin").password("password").roles("ADMIN")))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetEditProductView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/products/edit/{id}",product.getId()).with(SecurityMockMvcRequestPostProcessors.user("admin").password("password").roles("ADMIN")))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetProductListView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/products").with(SecurityMockMvcRequestPostProcessors.user("admin").password("password").roles("ADMIN")))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
