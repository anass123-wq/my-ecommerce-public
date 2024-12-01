package com.spring.securityservice.controller;

import com.spring.securityservice.model.Role;
import com.spring.securityservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = {"http://localhost:3004" ,"http://localhost:3006" ,"http://localhost:3007" ,"http://localhost:3008", "http://localhost:3003","http://localhost:3000","http://localhost:3005"})
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/user/username/{username}/roles")
    public List<Role> getRolesForUser(@PathVariable String username) {
        return roleService.getRolesForUser(username);
    }

    @PostMapping("/user/username/{username}/roles")
    public void addRoleToUser(@PathVariable String username, @RequestBody String roleName) {
        roleService.addRoleToUser(username, roleName);
    }

    @DeleteMapping("/user/username/{username}/roles/{roleName}")
    public void removeRoleFromUser(@PathVariable String username, @PathVariable String roleName) {
        roleService.removeRoleFromUser(username, roleName);
    }
}
