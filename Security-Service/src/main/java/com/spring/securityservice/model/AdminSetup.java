package com.spring.securityservice.model;

import com.spring.securityservice.repository.RoleRepository;
import com.spring.securityservice.repository.UserRepository;
import com.spring.securityservice.service.AuthenticationService;
import com.spring.securityservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AdminSetup {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


        @EventListener(ApplicationReadyEvent.class)
        public void addAdminUser() {
            if (userRepository.findByEmail("EmailADMIN@gmail.com").isEmpty()) {
                User admin = new User();
                admin.setFullName("ADMIN");
                admin.setPassword(passwordEncoder.encode("ADMIN123")); // تشفير كلمة المرور هنا
                admin.setEmail("EmailADMIN@gmail.com");
                userRepository.save(admin);
            }
        }
    }
