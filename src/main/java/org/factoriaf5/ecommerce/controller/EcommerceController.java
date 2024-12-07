package org.factoriaf5.ecommerce.controller;

import java.io.IOException;

import org.factoriaf5.ecommerce.dto.ProductDTO;
import org.factoriaf5.ecommerce.dto.UserDTO;
import org.factoriaf5.ecommerce.model.User;
import org.factoriaf5.ecommerce.service.ProductService;
import org.factoriaf5.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    UserService userService;

    @GetMapping
    public String home(Model model){
        model.addAttribute("products",productService.findAllProducts());
        return "index";
    }
    
    @GetMapping("/admin/products")
    public String list(Model model) {
        model.addAttribute("products",productService.findAllProducts());
        return "list";
    }

    @GetMapping("/admin/products/create")
    public String create() {
        return "create";
    }

    @PostMapping("/admin/products/create")
    public String create(@ModelAttribute ProductDTO productDTO,@RequestParam MultipartFile img) throws IOException {
        productService.saveProduct(productDTO,img);
        return "redirect:/ecommerce/products";
    }

    @GetMapping("/admin/products/edit/{id}")
    public String edit (@PathVariable Long id, Model model) {
        model.addAttribute("product",productService.findProductById(id));
        return "edit";
    }
    
    @PostMapping("/admin/products/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute ProductDTO productDTO,@RequestParam MultipartFile img) throws IOException {
        productDTO.setId(id);
        productService.saveProduct(productDTO, img);
        return "redirect:/ecommerce/products";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/ecommerce/products";
    }
    
    @GetMapping("/products/{id}")
    public String get(@PathVariable Long id, Model model) {
        model.addAttribute("product",productService.findProductById(id));
        return "product";
    }

    @GetMapping("/auth/login")
    public String login() {
        return "login";
    }

    @GetMapping("/auth/register")
    public String register() {
        return "register";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/{username}")
    public String profile(@PathVariable String username, @ModelAttribute UserDTO userDTO) {
        String signedUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(x -> x.getAuthority().equals(User.Role.ROLE_ADMIN.name()));
        if(signedUserUsername.equals(username)||isAdmin){
            userService.save(username,userDTO);
        }else{
            throw new AccessDeniedException("");
        }
        return "redirect:/ecommerce";
    }
    
    
    
    
    
}
