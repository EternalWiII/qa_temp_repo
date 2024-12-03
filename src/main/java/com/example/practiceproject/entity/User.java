package com.example.practiceproject.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

/**
 * User represents an entity that defines a user in the application.
 * It is mapped to the "users" table in the database and contains the user's ID,
 * email, username, password, and associated roles.
 */
@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(unique=true, nullable=false, length=20)
    private String username;

    @Column(nullable=false, length=64)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )

    private Collection<Role> roles;
}
