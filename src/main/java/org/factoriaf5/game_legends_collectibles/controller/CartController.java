package org.factoriaf5.game_legends_collectibles.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.factoriaf5.game_legends_collectibles.CookieUtils;
import org.factoriaf5.game_legends_collectibles.dto.CartItem;
import org.factoriaf5.game_legends_collectibles.model.Product;
import org.factoriaf5.game_legends_collectibles.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CookieUtils cookieUtils;

    @GetMapping
    public String getCartView(Model model, HttpServletRequest request) throws IOException {
        List<CartItem> cartItems = cookieUtils.getCartProductsFromRequestCookie(request);
        BigDecimal total = cartItems.stream()
                .map(item -> item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("cart", cartItems);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, @RequestParam Integer quantity,
                            HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<CartItem> cartItems = cookieUtils.getCartProductsFromRequestCookie(request);
        Product p = productService.findProductById(productId);
        if (p != null) {
            cartItems = cartItems.stream()
                    .peek(item -> {
                        if (item.getProductId().equals(productId)) {
                            item.setQuantity(item.getQuantity() + quantity);
                        }
                    })
                    .collect(Collectors.toList());
            if (cartItems.stream().noneMatch(item -> item.getProductId().equals(productId))) {
                cartItems.add(new CartItem(productId, quantity, p.getPrice(), p.getName()));
            }
        }
        Cookie cookie = cookieUtils.generateCartCookie(cartItems);
        response.addCookie(cookie);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId, HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        List<CartItem> cartItems = cookieUtils.getCartProductsFromRequestCookie(request);
        cartItems = cartItems.stream()
                .filter(item -> !item.getProductId().equals(productId))
                .collect(Collectors.toList());
        Cookie cookie = cookieUtils.generateCartCookie(cartItems);
        response.addCookie(cookie);
        return "redirect:/cart";
    }
}
