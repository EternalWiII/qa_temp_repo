package com.example.practiceproject.dto;

import lombok.Data;

/**
 * JwtRequest represents a request containing the user's authentication credentials.
 * It includes the username and password required for generating a JWT token.
 */
@Data
public class JwtRequest {
    private String username;
    private String password;
}
