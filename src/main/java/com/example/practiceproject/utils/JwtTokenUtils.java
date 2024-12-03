package com.example.practiceproject.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;



/**
 * JwtTokenUtils is a utility class for handling JWT (JSON Web Token) operations.
 * It provides methods for generating tokens, extracting user information, and retrieving roles from tokens.
 */
@Component
public class JwtTokenUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration lifetime;

    /**
     * Generates a JWT token for the specified user details.
     *
     * @param userDetails the UserDetails object containing user information
     * @return a String representing the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", authorities);

        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + lifetime.toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token
     * @return a String representing the username contained in the token
     */
    public String getUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Retrieves a list of roles from the provided JWT token.
     *
     * @param token the JWT token
     * @return a List of Strings representing the roles contained in the token
     */
    public List<String> getRoles(String token) {
        return getClaimsFromToken(token).get("roles", List.class);
    }

    /**
     * Parses the JWT token to extract claims.
     *
     * @param token the JWT token
     * @return the Claims object containing the claims from the token
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }
}

