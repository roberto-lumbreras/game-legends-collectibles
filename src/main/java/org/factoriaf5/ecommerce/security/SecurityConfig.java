package org.factoriaf5.ecommerce.security;

import org.factoriaf5.ecommerce.model.User;
import org.factoriaf5.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService){
        return new JwtAuthenticationFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception{
        http.csrf(x -> x.disable());
        http.sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(x -> x.disable());
        http.httpBasic(x -> x.disable());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(x -> x
            .requestMatchers("/ecommerce/profile","/ecommerce/profile/**","/ecommerce/order-summary").authenticated()
            .requestMatchers("/ecommerce/admin/**").hasAuthority(User.Role.ROLE_ADMIN.name())
            .requestMatchers("/**").permitAll()
            .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
            return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(user.getRole().name())
            .build();
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return x -> {
            User rootUser = User.builder()
                .role(User.Role.ROLE_ADMIN)
                .username("admin")
                .email("mockemail@mail.com")
                .address("mockAddress")
                .phoneNumber("mockPhoneNumber")
                .password(passwordEncoder.encode("password")).build();
            userRepository.save(rootUser);
        };
    }

}
