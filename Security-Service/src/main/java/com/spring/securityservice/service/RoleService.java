package com.spring.securityservice.service;

import com.spring.securityservice.model.Role;
import com.spring.securityservice.model.User;
import com.spring.securityservice.repository.RoleRepository;
import com.spring.securityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
    public List<Role> getRolesForUser(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return new ArrayList<>(user.get().getRoles());
        } else {
            throw new RuntimeException("User not found: " + username);
        }
    }

    public void addRoleToUser(String username, String roleName) {
        Optional<User> user = userRepository.findByEmail(username);
        Optional<Role> role = roleRepository.findByName(roleName);
        if (user != null && role != null) {
            user.get().getRoles().add(role.get());
            userRepository.save(user.get());
        } else {
            throw new RuntimeException("User or role not found.");
        }
    }

    public void removeRoleFromUser(String username, String roleName) {
        Optional<User> user = userRepository.findByEmail(username);
        Optional<Role> role = roleRepository.findByName(roleName);
        if (user != null && role != null) {
            user.get().getRoles().remove(role.get());
            userRepository.save(user.get());
        } else {
            throw new RuntimeException("User or role not found.");
        }
    }

}
