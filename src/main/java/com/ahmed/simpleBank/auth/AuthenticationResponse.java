package com.ahmed.simpleBank.auth;

import com.ahmed.simpleBank.dto.UserResponseDTO;


public class AuthenticationResponse {
    private String token;
    private long expirationTime;
    private UserResponseDTO user;

    public AuthenticationResponse(String token, long expirationTime, UserResponseDTO user) {
        this.token = token;
        this.expirationTime = expirationTime;
        this.user = user;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                ", expirationTime=" + expirationTime +
                ", user=" + user +
                '}';
    }
}
