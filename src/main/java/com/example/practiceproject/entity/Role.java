package com.example.practiceproject.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Role represents an entity that defines a user role in the application.
 * It is mapped to the "roles" table in the database and contains the role's ID and name.
 */
@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;
}
