package com.spring.salesorderservice.controller;

import com.spring.salesorderservice.dto.SalesLineDto;
import com.spring.salesorderservice.model.SalesLine;
import com.spring.salesorderservice.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
//@CrossOrigin(origins = {"http://localhost:3004" ,"http://localhost:3006" ,"http://localhost:3007" ,"http://localhost:3008", "http://localhost:3003","http://localhost:3000","http://localhost:3005"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/line")
public class LineController {
    @Autowired
    private LineService lineService;
    @PutMapping("/line/{id}")
    public ResponseEntity<SalesLine> updateSalesLine(@PathVariable Long id, @RequestBody SalesLineDto salesLineDto) {
        SalesLine updatedSalesLine = lineService.updateSalesLine(id, salesLineDto);
        return ResponseEntity.ok(updatedSalesLine);
    }
    @GetMapping("lines")
    public List<SalesLine> getSalesLines() {
        return lineService.getSalesLines();
    }
    @DeleteMapping("/line/{id}")
    public ResponseEntity<SalesLine> deleteSalesLine(@PathVariable Long id) {
        SalesLine deletedSalesLine = lineService.deleteSalesLine(id);
        return ResponseEntity.ok(deletedSalesLine);
    }
}
