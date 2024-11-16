package com.spring.securityservice.controller;

import com.spring.securityservice.dto.RegisterUserDto;
import com.spring.securityservice.model.Role;
import com.spring.securityservice.model.User;
import com.spring.securityservice.service.RoleService;
import com.spring.securityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = {"http://localhost:3004" ,"http://localhost:3006" ,"http://localhost:3007" ,"http://localhost:3008", "http://localhost:3003","http://localhost:3000","http://localhost:3005"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }


    @PostMapping("/admin/create-user")
    public ResponseEntity<User> createUser(@RequestBody RegisterUserDto request) {
        User newUser = new User();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        userService.createUserWithRoles(newUser, request.getRoles());
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/admin/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }

    @PostMapping("/admin/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.saveRole(role));
    }

    // Endpoint to update roles for an existing user
    @PutMapping("/update-roles/{userId}")
    public ResponseEntity<User> updateUserRoles(@PathVariable Integer userId, @RequestBody List<Role> roles) {
        User updatedUser = userService.updateRoles(userId, roles);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint to update user information
    @PutMapping("/update-user/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        return ResponseEntity.ok(userService.updateUser(userId, updatedUser));
    }

    // Endpoint to delete a user
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(Math.toIntExact(userId)));
    }
    @GetMapping("/user/username/{username}")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
    @GetMapping("/search")
    public ResponseEntity<List<RegisterUserDto>> searchAccounts(@RequestParam String query) {
        List<RegisterUserDto> results = userService.searchAccounts(query);
        return ResponseEntity.ok(results);
    }
}