package com.spring.purchaseorderservice.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Component
@FeignClient(name = "SupplierClient-Service", url = "http://localhost:8034")
public interface FeignClientSupplierService {
    @PutMapping("/SupplierClients/{totale}")
    void updateTotalOrder(@PathVariable("name") String name ,@PathVariable("totale")double totale);


    /* SupplierClient getSupplierClientByName(@PathVariable("name") String name);
     @GetMapping("/SupplierClients/id/{id}")
    SupplierClientDto getSupplierClientById(@PathVariable("id") Long id);
    @PostMapping("/SupplierClients/supplierClient")
    SupplierClientDto saveSupplierClient(@RequestBody SupplierClientDto supplierClientDto);*/
}

