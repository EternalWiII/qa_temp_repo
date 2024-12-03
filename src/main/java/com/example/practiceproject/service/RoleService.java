package com.example.practiceproject.service;

import com.example.practiceproject.entity.Role;
import com.example.practiceproject.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * RoleService provides functionalities for managing user roles in the application.
 * It interacts with the RoleRepository to retrieve role information.
 */
@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    /**
     * Retrieves the role object for the user role ("ROLE_USER").
     *
     * @return the Role object representing "ROLE_USER"
     */
    public Role getRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
