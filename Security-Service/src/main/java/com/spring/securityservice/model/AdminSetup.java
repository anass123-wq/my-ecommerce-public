package com.spring.securityservice.model;

import com.spring.securityservice.repository.RoleRepository;
import com.spring.securityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSetup implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminSetup(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if the admin user already exists
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            // Create Admin role if it doesn't exist
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

            // Create User role if it doesn't exist
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

            // Create admin user
            User admin = new User();
            admin.setFullName("admin");
            admin.setPassword(new BCryptPasswordEncoder().encode("adminPassword")); // Don't forget to encode the password!
            admin.setEmail("admin@example.com");
            admin.getRoles().add(adminRole);
            admin.getRoles().add(userRole);  // Admin can also have User role

            // Save user to the repository
            userRepository.save(admin);
        }
    }
}
