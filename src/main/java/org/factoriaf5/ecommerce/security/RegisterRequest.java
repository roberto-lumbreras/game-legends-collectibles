package org.factoriaf5.ecommerce.security;

public record RegisterRequest(String username,
        String phoneNumber,
        String address,
        String email,
        String password) {

}
