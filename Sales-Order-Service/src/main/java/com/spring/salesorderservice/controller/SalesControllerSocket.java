package com.spring.salesorderservice.controller;
import com.spring.salesorderservice.model.SalesOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//@PreAuthorize("hasAuthority('ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = {"http://localhost:3004" ,"http://localhost:3006" ,"http://localhost:3007" ,"http://localhost:3008", "http://localhost:3003","http://localhost:3000","http://localhost:3005"})
@RestController
@RequestMapping("/")
public class SalesControllerSocket {
    @Autowired
    private SimpMessagingTemplate template;
    @PostMapping("/add")
    public String addSale(@RequestBody SalesOrder sale) {
        template.convertAndSend("/topic/sales", sale);
        return "added!";
    }
}
