package com.example.practiceproject.repository;

import com.example.practiceproject.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RoleRepository is a Spring Data repository interface for accessing
 * role entities in the database. It extends CrudRepository to provide
 * basic CRUD operations for the Role entity.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    /**
     * Retrieves a role by its name.
     *
     * @param Name the name of the role
     * @return an Optional containing the role if found, or empty if not
     */
    Optional<Role> findByName(String Name);
}
