package org.factoriaf5.ecommerce.controller;

import java.io.IOException;

import org.factoriaf5.ecommerce.dto.ProductDTO;
import org.factoriaf5.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
@RequestMapping("/ecommerce")
public class EcommerceController {
    @Autowired
    ProductService productService;

    @GetMapping
    public String home(){
        return "index";
    }
    
    @GetMapping("/products")
    public String list() {
        return "list";
    }

    @GetMapping("/products/create")
    public String create() {
        return "create";
    }

    @PostMapping("/products/create")
    public String create(@ModelAttribute ProductDTO productDTO,@RequestParam MultipartFile img) throws IOException {
        productService.createProduct(productDTO,img);
        return "list";
    }
    
    
}
