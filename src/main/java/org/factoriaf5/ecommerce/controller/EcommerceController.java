package org.factoriaf5.ecommerce.controller;

import java.io.IOException;

import org.factoriaf5.ecommerce.dto.ProductDTO;
import org.factoriaf5.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String home(Model model){
        model.addAttribute("products",productService.findAllProducts());
        return "index";
    }
    
    @GetMapping("/products")
    public String list(Model model) {
        model.addAttribute("products",productService.findAllProducts());
        return "list";
    }

    @GetMapping("/products/create")
    public String create() {
        return "create";
    }

    @PostMapping("/products/create")
    public String create(@ModelAttribute ProductDTO productDTO,@RequestParam MultipartFile img) throws IOException {
        productService.saveProduct(productDTO,img);
        return "redirect:/ecommerce/products";
    }

    @GetMapping("/products/edit/{id}")
    public String edit (@PathVariable Long id, Model model) {
        model.addAttribute("product",productService.findProductById(id));
        return "edit";
    }
    
    @PostMapping("/products/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute ProductDTO productDTO,@RequestParam MultipartFile img) throws IOException {
        productDTO.setId(id);
        productService.saveProduct(productDTO, img);
        return "redirect:/ecommerce/products";
    }

    @GetMapping("/products/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/ecommerce/products";
    }
    
    @GetMapping("/products/{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("product",productService.findProductById(id));
        return "product";
    }
    
    
    
}
