package com.spring.purchaseorderservice.controller;

import com.spring.purchaseorderservice.model.LineReturn;
import com.spring.purchaseorderservice.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/lineReturn")
public class ReturnController {
    @Autowired
    private ReturnService returnService;
    @GetMapping
    public List<LineReturn> getLineReturn() {
       return returnService.getAllLineReturn();
    }
    @PostMapping("/line/create")
    public LineReturn addLineReturn(LineReturn lineReturn) {
        return returnService.addLineReturn(lineReturn);
    }
}
