package org.factoriaf5.ecommerce.controller;

import java.util.List;

import org.factoriaf5.ecommerce.model.Product;
import org.factoriaf5.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
}
