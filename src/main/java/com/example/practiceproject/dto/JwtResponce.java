package com.example.practiceproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * JwtResponse represents the response containing the JWT token
 * returned after successful authentication.
 */
@Data
@AllArgsConstructor
public class JwtResponce {
    private String token;
}
