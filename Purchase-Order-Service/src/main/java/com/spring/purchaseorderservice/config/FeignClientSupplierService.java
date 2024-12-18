package com.spring.purchaseorderservice.config;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name = "SupplierClient-Service")
public interface FeignClientSupplierService {
    @Bean
    default RequestInterceptor feignRequestInterceptor() {
        return requestTemplate -> {
            String serviceName = "PurchService";
            requestTemplate.header("Source-Service", serviceName);
        };
    }
    @PutMapping("/SupplierClients/{totale}/{name}")
    void updateTotalOrder(@PathVariable("name") String name ,@PathVariable("totale")double totale);


    /* SupplierClient getSupplierClientByName(@PathVariable("name") String name);
     @GetMapping("/SupplierClients/id/{id}")
    SupplierClientDto getSupplierClientById(@PathVariable("id") Long id);
    @PostMapping("/SupplierClients/supplierClient")
    SupplierClientDto saveSupplierClient(@RequestBody SupplierClientDto supplierClientDto);*/
}

