package com.spring.purchaseorderservice.config;

import com.spring.purchaseorderservice.model.PaymentStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Payment-Service" ,url = "http://localhost:8035/payment")
public interface FeignPaiement {
    @PostMapping("/update/ofFeign/{OrderId}")
    void updatePayment(@PathVariable Long OrderId, @RequestParam double amount, @RequestParam String status) throws Exception;
}
