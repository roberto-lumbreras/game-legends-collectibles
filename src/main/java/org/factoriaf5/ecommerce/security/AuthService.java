package org.factoriaf5.ecommerce.security;

import org.factoriaf5.ecommerce.model.User;
import org.factoriaf5.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        return jwtUtils.generateToken(loginRequest.username());
    }
    public void register(RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .address(registerRequest.address())
                .role(User.Role.USER)
                .email(registerRequest.email())
                .phoneNumber(registerRequest.phoneNumber())
                .build();
        userRepository.save(user);
    }

}
