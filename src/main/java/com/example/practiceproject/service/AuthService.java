package com.example.practiceproject.service;

import com.example.practiceproject.dto.JwtRequest;
import com.example.practiceproject.dto.JwtResponce;
import com.example.practiceproject.dto.RegistrationUserDto;
import com.example.practiceproject.dto.UserDto;
import com.example.practiceproject.entity.User;
import com.example.practiceproject.exception.ApplicationError;
import com.example.practiceproject.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * AuthService handles authentication and user registration logic in the application.
 * It is responsible for creating authentication tokens and managing user registration.
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    /**
     * Creates an authentication token for the user based on the provided credentials.
     *
     * @param authRequest the JWT request containing the user's credentials
     * @return a ResponseEntity containing the JWT token if authentication is successful,
     *         or an error response if authentication fails
     */
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            System.out.println(authRequest.getUsername());
            System.out.println(authRequest.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ApplicationError(HttpStatus.UNAUTHORIZED.value(), "Bad Credentials"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponce(token));
    }

    /**
     * Creates a new user in the system based on the provided registration details.
     *
     * @param registrationUserDto the registration details for the new user
     * @return a ResponseEntity containing the created user's information if registration is successful,
     *         or an error response if registration fails
     */
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if(!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.value(), "Passwords do not match"), HttpStatus.BAD_REQUEST);
        }

        if(customUserDetailsService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.value(), "User with such username exists"), HttpStatus.BAD_REQUEST);
        }

        User user = customUserDetailsService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }
}
