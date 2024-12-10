package org.factoriaf5.game_legends_collectibles.security;

public record RegisterRequest(String username,
                String phoneNumber,
                String address,
                String email,
                String password) {

}
