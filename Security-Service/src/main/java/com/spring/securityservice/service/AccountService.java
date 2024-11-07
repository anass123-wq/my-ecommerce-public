package com.spring.securityservice.service;

import com.spring.securityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    public List<AccountDto> searchAccounts(String query) {
        return userRepository.findByUsernameContainingOrRoles_NameContaining(query, query)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
