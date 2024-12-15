package org.factoriaf5.game_legends_collectibles.service;

import org.factoriaf5.game_legends_collectibles.dto.LoginRequest;
import org.factoriaf5.game_legends_collectibles.dto.RegisterRequest;
import org.factoriaf5.game_legends_collectibles.model.User;
import org.factoriaf5.game_legends_collectibles.repository.UserRepository;
import org.factoriaf5.game_legends_collectibles.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    
    public String login(LoginRequest loginRequest) {
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()))); 
        return jwtUtils.generateToken();
    }
    public void register(RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .address(registerRequest.address())
                .role(User.Role.ROLE_USER)
                .email(registerRequest.email())
                .phoneNumber(registerRequest.phoneNumber())
                .build();
        userRepository.save(user);
    }

}
