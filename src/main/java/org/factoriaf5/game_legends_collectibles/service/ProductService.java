package org.factoriaf5.game_legends_collectibles.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.factoriaf5.game_legends_collectibles.dto.ProductDTO;
import org.factoriaf5.game_legends_collectibles.model.Product;
import org.factoriaf5.game_legends_collectibles.repository.ProductRepository;
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
        File img = new File("/images",imgUrl.replaceFirst("/", ""));
        if(img.exists()&&!imgUrl.contains("default")){
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
        final String UPLOAD_DIR = "/images";
        File uploadDir = new File(UPLOAD_DIR);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }
        String filename = System.currentTimeMillis()+"_"+img.getOriginalFilename();
        uploadDir = new File(uploadDir,filename);
        img.transferTo(uploadDir);
        return "/"+filename;
    }

}
