package com.example.practiceproject.repository;

import com.example.practiceproject.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository is a Spring Data repository interface for accessing
 * user entities in the database. It extends CrudRepository to provide
 * basic CRUD operations for the User entity.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user
     * @return an Optional containing the user if found, or empty if not
     */
    Optional<User> findByUsername(String username);
}
