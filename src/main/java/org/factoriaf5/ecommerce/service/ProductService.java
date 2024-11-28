package org.factoriaf5.ecommerce.service;

import org.factoriaf5.ecommerce.model.Product;
import org.factoriaf5.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    /* public List<Product>findAllProducts(){
        return productRepository.findAll();
    } */

    public Product findProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }

    public Product updateProduct(Product product){
        return productRepository.save(product);
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }
}
