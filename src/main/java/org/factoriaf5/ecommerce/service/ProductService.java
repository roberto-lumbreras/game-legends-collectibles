package org.factoriaf5.ecommerce.service;

import java.io.File;
import java.io.IOException;

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

    public Product createProduct(ProductDTO productDTO,MultipartFile img) throws IOException{
        Product product = new Product();
        product.setDescription(productDTO.getDescription());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setImgUrl(imageProcessingService(img));
        return productRepository.save(product);
    }

    private String imageProcessingService(MultipartFile file) throws IOException{
        final String UPLOAD_DIR = System.getProperty("user.dir")+"/images";
        File uploadDir = new File(UPLOAD_DIR);
        System.out.println("ruta del directorio de carga de imagen -> "+uploadDir.getAbsolutePath());
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }
        String filename = file.getOriginalFilename()+"_"+System.currentTimeMillis();
        uploadDir = new File(uploadDir,filename);
        System.out.println("ruta del archivo de imagen -> "+uploadDir.getAbsolutePath());
        file.transferTo(uploadDir);
        return "images/"+filename;
    }
}
