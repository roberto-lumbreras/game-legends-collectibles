package org.factoriaf5.game_legends_collectibles.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.factoriaf5.game_legends_collectibles.CookieUtils;
import org.factoriaf5.game_legends_collectibles.dto.CartItem;
import org.factoriaf5.game_legends_collectibles.dto.OrderDTO;
import org.factoriaf5.game_legends_collectibles.dto.UserDTO;
import org.factoriaf5.game_legends_collectibles.model.Order;
import org.factoriaf5.game_legends_collectibles.model.OrderDetail;
import org.factoriaf5.game_legends_collectibles.model.User;
import org.factoriaf5.game_legends_collectibles.service.OrderService;
import org.factoriaf5.game_legends_collectibles.service.ProductService;
import org.factoriaf5.game_legends_collectibles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CookieUtils cookieUtils;

    @GetMapping
    public String getOrdersView(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        List<OrderDTO> orders = orderService.findByUser(user).stream()
                .map(order -> new OrderDTO(order.getOrderNumber(), order.getCreatedAt().toLocalDate(), order.getTotalAmount()))
                .collect(Collectors.toList());
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/{order-number}")
    public String getOrderDetailsView(@PathVariable("order-number") Long orderNumber, Model model) {
        List<OrderDetail> details = orderService.findByOrderNumber(orderNumber).getDetails();
        model.addAttribute("details", details);
        return "order-details";
    }

    @GetMapping("/save")
    public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<CartItem> cartItems = cookieUtils.getCartProductsFromRequestCookie(request);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);

        List<OrderDetail> details = cartItems.stream()
                .map(item -> OrderDetail.builder()
                        .product(productService.findProductById(item.getProductId()))
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        BigDecimal totalAmount = details.stream()
                .map(detail -> detail.getUnitPrice().multiply(new BigDecimal(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

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

        @GetMapping("/summary")
    public String getOrderSummaryView(HttpServletRequest request, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        UserDTO userDTO = new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getAddress(),
                user.getPhoneNumber());
        List<CartItem> cartItems = cookieUtils.getCartProductsFromRequestCookie(request);
        BigDecimal total = new BigDecimal(0);
        for (CartItem item : cartItems) {
            total = total.add(item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        model.addAttribute("cart", cartItems);
        model.addAttribute("total", total);
        model.addAttribute("user", userDTO);
        return "order-summary";
    }
}
