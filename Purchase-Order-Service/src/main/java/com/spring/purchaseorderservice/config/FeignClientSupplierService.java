package com.spring.purchaseorderservice.config;

import com.spring.purchaseorderservice.dto.SupplierClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SupplierClient-Service")
public interface FeignClientSupplierService {

    @GetMapping("/SupplierClients/{id}")
    SupplierClientDto getSupplierClientById(@PathVariable("id") Long id);

    @GetMapping("/SupplierClients/{name}")
    SupplierClientDto getSupplierClientByName(@PathVariable("name") String name);
}
