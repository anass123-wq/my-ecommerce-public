package com.spring.supplierclientservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = {"http://localhost:3004" ,"http://localhost:3006" ,"http://localhost:3007" ,"http://localhost:3008", "http://localhost:3003","http://localhost:3000","http://localhost:3005"})
@RestController
@RequestMapping("SupplierClients")
public class SupplierClientController {
    @Autowired
    private AuthorizationClient authorizationClient;

    @Autowired
    private SupplierClientService clientSupplierService;
    @PreAuthorize("hasAuthority('CREATE_SUPPLIERCLIENT')")
    @PostMapping("/supplierClient")
    public SupplierClient saveSupplierClient(@RequestBody SupplierClient supplierClient) throws AccessDeniedException {
        if (!authorizationClient.doesPermissionExist(List.of("CREATE_SUPPLIERCLIENT", "ADMIN"))) {
            throw new AccessDeniedException("Permission CREATE_SUPPLIERCLIENT does not exist.");
        }
        return clientSupplierService.saveSupplierClient(supplierClient);
    }
    @PreAuthorize("hasAuthority('VIEW_SUPPLIERCLIENT')")
    @GetMapping("/supplierClient")
    public List<SupplierClient> getAllSupplierClients() throws AccessDeniedException {
        if (!authorizationClient.doesPermissionExist(List.of("VIEW_SUPPLIERCLIENT" ,"ADMIN"))) {
            throw new AccessDeniedException("Permission VIEW_SUPPLIERCLIENT does not exist.");
        }
        return clientSupplierService.getAllSupplierClients();
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/name/{name}")
    public SupplierClient getSupplierClientByName(@PathVariable("name") String name) throws AccessDeniedException {
        if (!authorizationClient.doesPermissionExist(List.of("USER", "ADMIN"))) {
            throw new AccessDeniedException("Permission USER does not exist.");
        }
        return clientSupplierService.getSupplierClientByName(name);
    }
    @PreAuthorize("hasAuthority({'GET_ID'})")
    @GetMapping("/id/{id}")
    public SupplierClient getSupplierClientById(@PathVariable("id") long id) throws AccessDeniedException {
        if (!authorizationClient.doesPermissionExist(List.of("GET_ID", "ADMIN"))) {
            throw new AccessDeniedException("Permission GET_ID does not exist.");
        }
        return clientSupplierService.getSupplierClientById(id);
    }
    @PreAuthorize("hasAuthority({'DELETE' , 'DELETE_SUPPLIERCLIENT'}) ")
    @DeleteMapping("/Delete")
    public SupplierClient deleteSupplierClient(@RequestBody long id) throws AccessDeniedException {
        if (!authorizationClient.doesPermissionExist(List.of("DELETE_SUPPLIERCLIENT", "ADMIN")) ) {
            throw new AccessDeniedException("Permission  does not exist.");
        }
        return clientSupplierService.deleteSupplierClientById(id);
    }
    @GetMapping("/{email}")
    public SupplierClient getSupplierClientByEmail(@PathVariable("email") String email) throws AccessDeniedException {
        if (!authorizationClient.doesPermissionExist(Collections.singletonList("ADMIN"))) {
            throw new AccessDeniedException("Permission  does not exist.");
        }
        return clientSupplierService.findByEmail(email);
    }
    @GetMapping("/search")
    public ResponseEntity<List<SupplierClient>> searchSupplarClients(@RequestParam String query) throws AccessDeniedException {
        if (!authorizationClient.doesPermissionExist(Collections.singletonList("ADMIN"))) {
            throw new AccessDeniedException("Permission ADMIN does not exist.");
        }
        List<SupplierClient> results = clientSupplierService.searchSupplarClients(query);
        return ResponseEntity.ok(results
        );
    }
}

    /*
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