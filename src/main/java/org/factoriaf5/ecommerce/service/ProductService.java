package org.factoriaf5.ecommerce.service;

import org.factoriaf5.ecommerce.dto.ProductDTO;
import org.factoriaf5.ecommerce.model.Product;
import org.factoriaf5.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Product createProduct(ProductDTO productDTO){
        Product product = new Product();
        product.setDescription(productDTO.getDescription());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        //product.setImg(imageProcessingService(productDTO.getImg()));
        return productRepository.save(product);
    }

    private String imageProcessingService(MultipartFile file){
        return "";
    }
}
