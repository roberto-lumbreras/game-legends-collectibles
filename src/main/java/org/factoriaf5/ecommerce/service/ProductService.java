package org.factoriaf5.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    public List<Product>findAllProducts(){
        return productRepository.findAll();
    }

    public Product findProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProductById(Long id){
        Product productToDelete = findProductById(id);
        if(productToDelete!=null){
            String imgUrl = productToDelete.getImgUrl();
            if(imgUrl!=null){
                imageDeletionService(imgUrl);
            }
            productRepository.deleteById(id);
        }
    }

    private void imageDeletionService(String imgUrl) {
        imgUrl = System.getProperty("user.dir")+"/src/main/resources/static"+imgUrl;
        File img = new File(imgUrl);
        if(img.exists()){
            img.delete();
        }
    }

    public Product saveProduct(ProductDTO productDTO,MultipartFile img) throws IOException{
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setDescription(productDTO.getDescription());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        if(!img.isEmpty()){
            if(product.getId()!=null){
                String url = findProductById(product.getId()).getImgUrl();
                if(url!=null){
                    imageDeletionService(url);
                }
            }
            product.setImgUrl(imageCreationService(img));
        }else if(product.getId()!=null){
            product.setImgUrl(findProductById(product.getId()).getImgUrl());
        }
        return productRepository.save(product);
    }

    private String imageCreationService(MultipartFile img) throws IOException{
        final String UPLOAD_DIR = System.getProperty("user.dir")+"/src/main/resources/static/images";
        File uploadDir = new File(UPLOAD_DIR);
        System.out.println("ruta del directorio de carga de imagen -> "+uploadDir.getAbsolutePath());
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }
        String filename = img.getOriginalFilename()+"_"+System.currentTimeMillis();
        uploadDir = new File(uploadDir,filename);
        System.out.println("ruta del archivo de imagen -> "+uploadDir.getAbsolutePath());
        img.transferTo(uploadDir);
        return "/images/"+filename;
    }

}
