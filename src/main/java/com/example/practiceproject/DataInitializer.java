package com.example.practiceproject;

import com.example.practiceproject.entity.Role;
import com.example.practiceproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * DataInitializer is a component that initializes the application data on startup.
 * It implements CommandLineRunner to execute code after the Spring application context is loaded.
 * This class specifically sets up default user roles in the database if they do not already exist.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Executes on application startup to initialize roles in the database.
     * It checks for the existence of "ROLE_USER" and "ROLE_ADMIN" roles,
     * and creates them if they do not exist.
     *
     * @param args command line arguments passed to the application (not used)
     * @throws Exception if an error occurs during initialization
     */
    @Override
    public void run(String... args) throws Exception {
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        for (String roleName : roles) {
            roleRepository.findByName(roleName).orElseGet(() -> {
                Role role = new Role();
                role.setName(roleName);
                return roleRepository.save(role);
            });
        }
    }
}