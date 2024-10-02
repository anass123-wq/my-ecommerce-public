package com.spring.supplierclientservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("SupplierClients")
public class SupplierClientController {

    @Autowired
    private SupplierClientService clientSupplierService;

    // Client Endpoints @PathVariable("name")
    // Supplier Endpoints
    @PostMapping("/supplierClient")
    public ResponseEntity<SupplierClient> saveSupplierClient(@RequestBody SupplierClient supplierClient) {
        return ResponseEntity.ok(clientSupplierService.saveSupplierClient(supplierClient));
    }

    @GetMapping("/supplierClient")
    public ResponseEntity<List<SupplierClient>> getAllSupplierClients() {
        return ResponseEntity.ok(clientSupplierService.getAllSupplierClients());
    }
    @GetMapping("/{name}")
    public ResponseEntity<SupplierClient> getSupplierClientByName(@PathVariable("name") String name){
        return ResponseEntity.ok(clientSupplierService.getSupplierClientByName(name));
    }
    @GetMapping("/{id}")
    public ResponseEntity<SupplierClient> getSupplierClientById(@PathVariable("id") long id){
        return ResponseEntity.ok(clientSupplierService.getSupplierClientById(id));
    }
}
