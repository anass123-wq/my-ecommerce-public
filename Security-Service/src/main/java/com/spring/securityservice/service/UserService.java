package com.spring.securityservice.service;

import com.spring.securityservice.dto.RegisterUserDto;
import com.spring.securityservice.model.Role;
import com.spring.securityservice.model.User;
import com.spring.securityservice.repository.RoleRepository;
import com.spring.securityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder ;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public void createUserWithRoles(User user, Set<Role> roleNames) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        for (Role roleName : roleNames) {
            addRoleToUser(user.getUsername(), String.valueOf(roleName));
        }
    }


    private void addRoleToUser(String username, String roleName) {
        Optional<User> userOptional = userRepository.findByEmail(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Role> role= roleRepository.findByName(roleName);
            user.getRoles().add(role.get());
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found: " + username);
        }
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public User updateUser(Long userId, User updatedUser) {
        Optional<User> user = userRepository.findByEmail(updatedUser.getEmail());
        Optional<Role> role = roleRepository.findByName(updatedUser.getRoles().toString());
        user.get().getRoles().add(role.get());
        return userRepository.save(user.get());
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }


    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    public User updateRoles(Integer userId, List<Role> roles) {
        User user = userRepository.findById(userId).orElse(null);
        for (Role role : roles) {
            assert user != null;
            user.getRoles().remove(role);
        }
        assert user != null;
        user.getRoles().addAll(roles);
        return userRepository.save(user);
    }
    private Role role;

    public List<RegisterUserDto> searchAccounts(String query) {
        List<User> users = userRepository.findByUsernameContainingOrRoles_NameContaining(query);

        return users.stream()
                .map(user -> new RegisterUserDto(user.getUsername(), user.getRoles()))
                .collect(Collectors.toList());
    }
}