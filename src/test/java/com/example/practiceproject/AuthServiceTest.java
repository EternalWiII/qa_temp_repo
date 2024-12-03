package com.example.practiceproject;

import com.example.practiceproject.dto.JwtRequest;
import com.example.practiceproject.dto.JwtResponce;
import com.example.practiceproject.dto.RegistrationUserDto;
import com.example.practiceproject.dto.UserDto;
import com.example.practiceproject.entity.Role;
import com.example.practiceproject.entity.User;
import com.example.practiceproject.exception.ApplicationError;
import com.example.practiceproject.repository.UserRepository;
import com.example.practiceproject.service.AuthService;
import com.example.practiceproject.service.CustomUserDetailsService;
import com.example.practiceproject.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.practiceproject.utils.JwtTokenUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private RegistrationUserDto registrationUserDto;

    @Mock
    private User user;

    @Mock
    private ApplicationError applicationError;

    @Mock
    private JwtRequest authRequest;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtils jwtTokenUtils;

    @Mock
    private Role role;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNewUser_PasswordsDoNotMatch() {
        when(registrationUserDto.getPassword()).thenReturn("password123");
        when(registrationUserDto.getConfirmPassword()).thenReturn("password124");

        ResponseEntity<?> response = authService.createNewUser(registrationUserDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Passwords do not match", ((ApplicationError) response.getBody()).getMessage());
    }

    @Test
    public void testCreateNewUser_UserAlreadyExists() {
        when(registrationUserDto.getUsername()).thenReturn("existingUser");
        when(registrationUserDto.getPassword()).thenReturn("password123");
        when(registrationUserDto.getConfirmPassword()).thenReturn("password123");

        when(customUserDetailsService.findByUsername("existingUser")).thenReturn(java.util.Optional.of(new User()));

        ResponseEntity<?> response = authService.createNewUser(registrationUserDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User with such username exists", ((ApplicationError) response.getBody()).getMessage());
    }

    @Test
    public void testCreateNewUser_Success() {
        when(registrationUserDto.getPassword()).thenReturn("password123");
        when(registrationUserDto.getConfirmPassword()).thenReturn("password123");
        when(registrationUserDto.getUsername()).thenReturn("newUser");
        when(registrationUserDto.getEmail()).thenReturn("newUser@example.com");

        when(customUserDetailsService.findByUsername("newUser")).thenReturn(java.util.Optional.empty());
        when(customUserDetailsService.createNewUser(registrationUserDto)).thenReturn(user);

        when(user.getId()).thenReturn(1L);
        when(user.getUsername()).thenReturn("newUser");
        when(user.getEmail()).thenReturn("newUser@example.com");

        ResponseEntity<?> response = authService.createNewUser(registrationUserDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, ((UserDto) response.getBody()).getId());
        assertEquals("newUser", ((UserDto) response.getBody()).getUsername());
        assertEquals("newUser@example.com", ((UserDto) response.getBody()).getEmail());
    }

    @Test
    public void testCreateAuthToken_BadCredentials() {
        when(authRequest.getUsername()).thenReturn("testUser");
        when(authRequest.getPassword()).thenReturn("wrongPassword");

        doThrow(new BadCredentialsException("Bad credentials")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        ResponseEntity<?> response = authService.createAuthToken(authRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Bad Credentials", ((ApplicationError) response.getBody()).getMessage());
    }

    @Test
    public void testCreateAuthToken_Success() {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        when(authRequest.getUsername()).thenReturn("testUser");
        when(authRequest.getPassword()).thenReturn("correctPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        when(customUserDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtTokenUtils.generateToken(userDetails)).thenReturn("validJwtToken");

        ResponseEntity<?> response = authService.createAuthToken(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("validJwtToken", ((JwtResponce) response.getBody()).getToken());
    }

}
