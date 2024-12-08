package org.factoriaf5.ecommerce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.factoriaf5.ecommerce.dto.CartItem;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.io.Encoders;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieUtils {
    public List<CartItem> getCartProductsFromRequestCookie(HttpServletRequest request){
       Cookie[] cookies = request.getCookies();
       if(cookies!=null){
            for(Cookie c:cookies){
                if(c.getName().equals("cart")){
                    try {
                        return new ObjectMapper().readValue(Decoders.BASE64.decode(c.getValue()),new TypeReference<List<CartItem>>(){});
                    } catch (DecodingException | IOException ex) {
                        return new ArrayList<>();
                    }
                }
            }
       }
       return new ArrayList<>();
    }

    public Cookie generateCartCookie(List<CartItem> cartItems) throws JsonProcessingException{
        Cookie cookie = new Cookie("cart", Encoders.BASE64.encode(new ObjectMapper().writeValueAsString(cartItems).getBytes()));
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600*24*7);
        cookie.setPath("/");
        return cookie;
    }
}
