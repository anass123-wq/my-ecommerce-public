package com.spring.purchaseorderservice.config;

import com.spring.purchaseorderservice.model.PaymentStatus;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "SupplierClient-Service" ,url = "http://localhost:8034/SupplierClients")
public interface FeignClientSupplierService {
    @Bean
    default RequestInterceptor feignRequestInterceptor() {
        return requestTemplate -> {
            String serviceName = "PurchService";
            requestTemplate.header("Source-Service", serviceName);
        };
    }
    @PutMapping("/SupplierClients/{name}")
    void updateTotalOrder(@PathVariable("name") String name , @RequestParam("totale")double totale,@RequestParam("sourceService")  String sourceService,@RequestParam(value = "paymentStatus", required = false) PaymentStatus paymentStatus);


    /* SupplierClient getSupplierClientByName(@PathVariable("name") String name);
     @GetMapping("/SupplierClients/id/{id}")
    SupplierClientDto getSupplierClientById(@PathVariable("id") Long id);
    @PostMapping("/SupplierClients/supplierClient")
    SupplierClientDto saveSupplierClient(@RequestBody SupplierClientDto supplierClientDto);*/
}

