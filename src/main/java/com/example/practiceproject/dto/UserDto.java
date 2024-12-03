package com.example.practiceproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * UserDto is a Data Transfer Object (DTO) that represents a user
 * in the application. It contains the user's ID, username, and email.
 */
@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
}
