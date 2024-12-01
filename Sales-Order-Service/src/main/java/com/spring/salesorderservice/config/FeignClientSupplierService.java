package com.spring.salesorderservice.config;

import com.spring.salesorderservice.dto.SupplierClientDto;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "SupplierClient-Service")
public interface FeignClientSupplierService {
    @Bean
    public default RequestInterceptor feignRequestInterceptor() {
        return requestTemplate -> {
            String serviceName = "SalesService";
            requestTemplate.header("Source-Service", serviceName);

        };
    }

    @PutMapping("/SupplierClients/{totale}")
    void updateTotalOrder(@PathVariable("name") String name ,@PathVariable("totale")double totale);
/*
    @GetMapping("/SupplierClients/{id}")
    SupplierClientDto getSupplierClientById(@PathVariable("id") Long id);

    @GetMapping("/SupplierClients/{name}")
    SupplierClientDto getSupplierClientByName(@PathVariable("name") String name);
    @PostMapping("/SupplierClients/supplierClient")
    SupplierClientDto saveSupplierClient(@RequestBody SupplierClient supplierClient);*/
}
