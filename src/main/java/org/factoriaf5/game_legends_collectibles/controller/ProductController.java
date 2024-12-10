package org.factoriaf5.game_legends_collectibles.controller;

import java.io.IOException;

import org.factoriaf5.game_legends_collectibles.dto.ProductDTO;
import org.factoriaf5.game_legends_collectibles.service.ProductService;
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
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getProductListView(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "product-list";
    }

    @GetMapping("/create")
    public String getCreateProductView() {
        return "product-create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute ProductDTO productDTO, @RequestParam MultipartFile img) throws IOException {
        productService.saveProduct(productDTO, img);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String getEditProductView(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findProductById(id));
        return "product-edit";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute ProductDTO productDTO, @RequestParam MultipartFile img)
            throws IOException {
        productDTO.setId(id);
        productService.saveProduct(productDTO, img);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }
}
