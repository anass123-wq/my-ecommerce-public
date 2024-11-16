package com.spring.purchaseorderservice.config;

import com.spring.purchaseorderservice.dto.SupplierClientDto;
import com.spring.purchaseorderservice.model.SupplierClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Component
@FeignClient(name = "SupplierClient-Service", url = "http://localhost:8034")
public interface FeignClientSupplierService {

   @GetMapping("/SupplierClients/name/{name}")
    SupplierClient getSupplierClientByName(@PathVariable("name") String name);
    /*    @GetMapping("/SupplierClients/id/{id}")
    SupplierClientDto getSupplierClientById(@PathVariable("id") Long id);
    @PostMapping("/SupplierClients/supplierClient")
    SupplierClientDto saveSupplierClient(@RequestBody SupplierClientDto supplierClientDto);*/
}

