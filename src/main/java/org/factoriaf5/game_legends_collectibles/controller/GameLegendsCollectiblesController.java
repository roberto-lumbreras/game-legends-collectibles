package org.factoriaf5.game_legends_collectibles.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.factoriaf5.game_legends_collectibles.CookieUtils;
import org.factoriaf5.game_legends_collectibles.dto.CartItem;
import org.factoriaf5.game_legends_collectibles.dto.OrderDTO;
import org.factoriaf5.game_legends_collectibles.dto.ProductDTO;
import org.factoriaf5.game_legends_collectibles.dto.UserDTO;
import org.factoriaf5.game_legends_collectibles.model.Order;
import org.factoriaf5.game_legends_collectibles.model.OrderDetail;
import org.factoriaf5.game_legends_collectibles.model.Product;
import org.factoriaf5.game_legends_collectibles.model.User;
import org.factoriaf5.game_legends_collectibles.service.OrderService;
import org.factoriaf5.game_legends_collectibles.service.ProductService;
import org.factoriaf5.game_legends_collectibles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.io.DecodingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class GameLegendsCollectiblesController {
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    CookieUtils cookieUtils;
    @Autowired
    OrderService orderService;

    @GetMapping("/")
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
        return "redirect:/admin/products";
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
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/products";
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
        return "redirect:/";
    }

    @PostMapping("/cart/add/{productId}")
    public String addToCart(@PathVariable Long productId, @RequestParam Integer quantity, HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException, DecodingException, IOException {
        List<CartItem> cartItems = cookieUtils.getCartProductsFromRequestCookie(request);
        Product p = productService.findProductById(productId);
        if(p!=null){
            boolean productExists = false;
            for(CartItem item:cartItems){
                if(Objects.equals(item.getProductId(), productId)){
                    productExists = true;
                    item.setQuantity(item.getQuantity()+quantity);
                }
            }
            if(!productExists){
                cartItems.add(new CartItem(productId, quantity, p.getPrice()));
            }
        }
        Cookie cookie = cookieUtils.generateCartCookie(cartItems);
        response.addCookie(cookie);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model, HttpServletRequest request) throws DecodingException, IOException {
        List<CartItem> cartItems = cookieUtils.getCartProductsFromRequestCookie(request);
        BigDecimal total = new BigDecimal(0);
        Product p;
        for(CartItem item:cartItems){
            p = productService.findProductById(item.getProductId());
            item.setProductName(p.getName());
            total = total.add(p.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        model.addAttribute("cart",cartItems);
        model.addAttribute("total",total);
        return "cart";
    }
    

    @GetMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId,  HttpServletResponse response, HttpServletRequest request) throws JsonProcessingException, DecodingException, IOException {
        List<CartItem> cartItems = cookieUtils.getCartProductsFromRequestCookie(request);
        Product p = productService.findProductById(productId);
        if(p!=null){
            cartItems = cartItems.stream().filter(x -> !x.getProductId().equals(productId)).collect(Collectors.toList());
        }
        Cookie cookie = cookieUtils.generateCartCookie(cartItems);
        response.addCookie(cookie);
        return "redirect:/cart";
    }

    @GetMapping("/order-summary")
    public String orderSummary(HttpServletRequest request, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        UserDTO userDTO = new UserDTO(
            user.getUsername(),
            user.getEmail(),
            user.getAddress(),
            user.getPhoneNumber());
        List<CartItem> cartItems = cookieUtils.getCartProductsFromRequestCookie(request);
        BigDecimal total = new BigDecimal(0);
        Product p;
        for(CartItem item:cartItems){
            p = productService.findProductById(item.getProductId());
            item.setProductName(p.getName());
            total = total.add(p.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        model.addAttribute("cart",cartItems);
        model.addAttribute("total",total);
        model.addAttribute("user",userDTO);
        return "order-summary";
    }

    @GetMapping("/save-order")
    public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        List<CartItem> cartItems = cookieUtils.getCartProductsFromRequestCookie(request);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        List<OrderDetail> details= new ArrayList<>();
        OrderDetail detail;
        BigDecimal totalAmount = new BigDecimal(0);
        for(CartItem item:cartItems){
            detail = OrderDetail.builder()
            .product(productService.findProductById(item.getProductId()))
            .quantity(item.getQuantity())
            .unitPrice(item.getUnitPrice())
            .build();
            details.add(detail);
            totalAmount = totalAmount.add(item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        Order order = Order.builder()
            .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .orderNumber(System.currentTimeMillis())
            .totalAmount(totalAmount)
            .details(details)
            .user(user)
            .build();
        orderService.save(order);
        Cookie cookie = cookieUtils.deleteCartCookie();
        response.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        List<OrderDTO> orders = orderService.findByUser(user).stream().map(x -> new OrderDTO(x.getOrderNumber(), x.getCreatedAt().toLocalDate(),x.getTotalAmount())).collect(Collectors.toList());
        model.addAttribute("orders",orders);
        return "orders";
    }

    @GetMapping("/orders/{order-number}")
    public String orderDetails(@PathVariable(name="order-number") Long orderNumber,Model model){
        List<OrderDetail> details = orderService.findByOrderNumber(orderNumber).getDetails();
        model.addAttribute("details", details);
        return "order-details";
    }
    
    
    
    
    
    
    
    
    
    
}
