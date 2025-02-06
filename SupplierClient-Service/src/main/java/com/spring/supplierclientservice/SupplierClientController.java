package com.spring.supplierclientservice;

import jakarta.ws.rs.HeaderParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
//
@PreAuthorize("hasRole('ADMIN') or hasAuthority('ADMIN')")
@CrossOrigin(origins = {"http://localhost:3004" ,"http://localhost:3006" ,"http://localhost:3007" ,"http://localhost:3008", "http://localhost:3003","http://localhost:3000","http://localhost:3005"})
@RestController
@RequestMapping("/SupplierClients")
public class SupplierClientController {

    @Autowired
    private SupplierClientService clientSupplierService;
    @PreAuthorize("hasAuthority({'CREATE_SUPPLIERCLIENT','UPDATE' })")

    @PostMapping("/supplierClient")
    public SupplierClient saveSupplierClient(@RequestBody SupplierClient supplierClient) throws AccessDeniedException {
        return clientSupplierService.saveSupplierClient(supplierClient);
    }
    @PreAuthorize("hasAuthority('VIEW_SUPPLIERCLIENTS')")
    @GetMapping("/supplierClient")
    public List<SupplierClient> getAllSupplierClients() throws AccessDeniedException {
        return clientSupplierService.getAllSupplierClients();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/name/{name}")
    public SupplierClient getSupplierClientByName(@PathVariable("name") String name){
        return clientSupplierService.getSupplierClientByName(name);
    }
    @PreAuthorize("hasAuthority({'GET_ID'})")
    @GetMapping("/id/{id}")
    public SupplierClient getSupplierClientById(@PathVariable("id") long id){
        return clientSupplierService.getSupplierClientById(id);
    }
    @PreAuthorize("hasAuthority({'DELETE' , 'DELETE_SUPPLIERCLIENT'}) ")
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<String> deleteSupplierClient(@PathVariable("id") long id) {
        SupplierClient isDeleted = clientSupplierService.deleteSupplierClientById(id);

        if (isDeleted!=null) {
            return ResponseEntity.ok("SupplierClient with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SupplierClient not found with ID: " + id);
        }
    }
    @GetMapping("/{email}")
    public SupplierClient getSupplierClientByEmail(@PathVariable("email") String email){
        return clientSupplierService.findByEmail(email);
    }
    @GetMapping("/search")
    public ResponseEntity<List<SupplierClient>> searchSupplarClients(@RequestParam String query) throws AccessDeniedException {
        List<SupplierClient> results = clientSupplierService.searchSupplarClients(query);
        return ResponseEntity.ok(results
        );
    }
    @PreAuthorize("hasAuthority({'UPDATE'})")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplierClient(@PathVariable("id") long id, @RequestBody SupplierClient supplierClient) {
         clientSupplierService.updateSupplierClient(id,supplierClient);
         return ResponseEntity.ok(supplierClient);
    }
    @PutMapping("/SupplierClients/{name}")
    public ResponseEntity<?> updateTotalOrder(@PathVariable("name") String name, @RequestParam("totale") double totale, @RequestHeader("Source-Service")@RequestParam("sourceService") String sourceService,@RequestParam(value = "paymentStatus", required = false)String paymentStatus){
        Optional<SupplierClient> supplierClient = Optional.ofNullable(clientSupplierService.getSupplierClientByName(name));
        if (supplierClient.isPresent()) {
            SupplierClient client = supplierClient.get();
            if (sourceService.equals("SalesService")) {
                client.setTotalSeles(client.getTotalSeles() + totale);
                if (Objects.equals(paymentStatus, "UNPAID")){
                    client.setBorrowingPricSeles(client.getBorrowingPricSeles() + totale);
                }
            } else if (sourceService.equals("PurchService")) {
                client.setTotalePurch(client.getTotalePurch() + totale);
                if (Objects.equals(paymentStatus, "UNPAID")){
                    client.setBorrowingPricPurch(client.getBorrowingPricPurch() + totale);
                }
            }
            // Save the updated supplier client if necessary
            clientSupplierService.saveSupplierClient(client);
        }
        return ResponseEntity.ok().build();
    }
}
/*
 ROLE_DELETE_FILTER
ROLE_CREATE_FILTER
ROLE_VIEW_FILTER
CREATE
CREATE_PRODUCTS
VIEW_PRODUCTS
DELETE_PRODUCTS
UPDATE
USER
SEARCH
DELETE_SUPPLIERCLIENT
DELETE
GET_ID
CREATE_SUPPLIERCLIENT
VIEW_SUPPLIERCLIENTS
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
    }*/