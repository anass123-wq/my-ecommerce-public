package com.spring.salesorderservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/line")
public class ReturnController {
}
