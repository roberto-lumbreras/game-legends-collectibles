package org.factoriaf5.ecommerce.service;

import java.util.List;

import org.factoriaf5.ecommerce.model.Product;
import org.factoriaf5.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product>getAllProducts(){
        return productRepository.findAll();
    }
}
