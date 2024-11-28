package org.factoriaf5.ecommerce.controller;

import org.factoriaf5.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("ecommerce")
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
    
    
}
