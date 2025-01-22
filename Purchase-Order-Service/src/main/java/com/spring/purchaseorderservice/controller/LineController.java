package com.spring.purchaseorderservice.controller;

import com.spring.purchaseorderservice.dto.PurchaseLineDto;
import com.spring.purchaseorderservice.model.PurchaseLine;
import com.spring.purchaseorderservice.service.LineService;
import com.spring.purchaseorderservice.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/line")
public class LineController {
    @Autowired
    private LineService lineService;
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @DeleteMapping("/line/{id}")
    public ResponseEntity<PurchaseLine> deleteSalesLine(@PathVariable Long id) {
        PurchaseLine deletedPurchaseLine = lineService.deleteSalesLine(id);
        return ResponseEntity.ok(deletedPurchaseLine);
    }
    @PutMapping("/line/{id}")
    public ResponseEntity<PurchaseLine> updateSalesLine(@PathVariable Long id, @RequestBody PurchaseLineDto lineDto) {
        PurchaseLine updatedLine = lineService.updatePurchaseLine(id, lineDto);
        return ResponseEntity.ok(updatedLine);
    }
    @GetMapping
    public ResponseEntity<List<PurchaseLine>> getSalesLines() {
        return (ResponseEntity<List<PurchaseLine>>) lineService.getPurchaseLines();
    }
}
