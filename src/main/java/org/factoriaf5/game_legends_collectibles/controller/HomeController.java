package org.factoriaf5.game_legends_collectibles.controller;

import org.factoriaf5.game_legends_collectibles.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class HomeController {
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String getHomeView(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "index";
    }

    @GetMapping("/products/{id}")
    public String getProductView(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findProductById(id));
        return "product";
    }

}
