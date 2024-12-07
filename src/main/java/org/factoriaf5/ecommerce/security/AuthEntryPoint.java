package org.factoriaf5.ecommerce.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthEntryPoint implements AuthenticationEntryPoint{

    AuthenticationEntryPoint authenticationEntryPoint = new Http403ForbiddenEntryPoint();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        if(authException instanceof UsernameNotFoundException){
            Cookie cookie = new Cookie("token", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            response.sendRedirect("/ecommerce");
        }else{
            authenticationEntryPoint.commence(request, response, authException);
        }
    }
    
}
