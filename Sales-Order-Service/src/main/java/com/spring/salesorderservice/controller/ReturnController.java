package com.spring.salesorderservice.controller;

import com.spring.salesorderservice.model.LineReturn;
import com.spring.salesorderservice.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public LineReturn addLineReturn(@RequestBody LineReturn lineReturn) {
        return returnService.addLineReturn(lineReturn);
    }
    @PutMapping("/line/update")
    public LineReturn updateLineReturn(@RequestBody LineReturn lineReturn) {
        return returnService.updateLineReturn(lineReturn);
    }
    @DeleteMapping("/line/{id}")
    public ResponseEntity<?> deleteLineReturn(@PathVariable int id) {
        returnService.deleteLineReturnById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/line/{id}")
    public LineReturn getLineReturnById(@PathVariable int id) {
        return returnService.getLineReturnById(id);
    }
}
