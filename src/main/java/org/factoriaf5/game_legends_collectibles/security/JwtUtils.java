package org.factoriaf5.game_legends_collectibles.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils{
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    public SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public JwtParser parser(){
        return Jwts.parser().verifyWith(key()).build();
    }

    public String generateToken(String username){
        return Jwts.builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis()+expiration))
        .signWith(key())
        .compact();
    }

    public String getTokenFromRequest(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie: cookies){
                if(cookie.getName().equals("token")){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public boolean validateToken(String token){
        try{
            parser().parse(token);
            return true;
        }catch(ExpiredJwtException | MalformedJwtException | SecurityException | IllegalArgumentException e){
            return false;
        }
    }

    public String extractUsername(String token){
        return parser().parseSignedClaims(token).getPayload().getSubject();
    }
}
