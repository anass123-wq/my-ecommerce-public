package com.spring.paymentservice.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SupplierClient-Service" ,url = "http://localhost:8034/SupplierClients")
public interface FeignSupplierClient {
    @PutMapping("/Borrowing/{name}")
    public   void updateBorrowing(@PathVariable("name") String name, @RequestParam double amountPaid, @RequestParam String ordreType);
}
