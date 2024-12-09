package org.factoriaf5.game_legends_collectibles.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;




@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, HttpServletResponse response) {
        String token = authService.login(loginRequest);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        return "redirect:/";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest registerRequest, HttpServletResponse response) {
        authService.register(registerRequest);
        return login(new LoginRequest(registerRequest.username(), registerRequest.password()), response);
    }
    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
    
}
