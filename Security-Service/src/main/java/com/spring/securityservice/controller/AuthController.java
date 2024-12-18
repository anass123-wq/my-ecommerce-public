package com.spring.securityservice.controller;

import com.spring.securityservice.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtService jwtTokenService;

    @GetMapping("/token")
    public int getJwtToken() {
        return 0;
    }
}