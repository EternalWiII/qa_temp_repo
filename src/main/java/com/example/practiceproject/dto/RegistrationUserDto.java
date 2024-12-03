package com.example.practiceproject.dto;

import com.example.practiceproject.entity.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

/**
 * RegistrationUserDto is a Data Transfer Object (DTO) that contains
 * the user's registration information including username, email,
 * password, and a confirmation of the password.
 */
@Data
public class RegistrationUserDto {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
