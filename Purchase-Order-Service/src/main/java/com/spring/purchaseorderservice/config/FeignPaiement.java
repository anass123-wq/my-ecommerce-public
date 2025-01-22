package com.spring.purchaseorderservice.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Payment-Service" ,url = "http://localhost:8035/payment")
public interface FeignPaiement {
    @PostMapping("/update/ofFeign")
    void updatePayment(@RequestParam double amount);
}
