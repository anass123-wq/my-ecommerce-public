package com.spring.purchaseorderservice.controller;

import com.spring.purchaseorderservice.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
public class SalesControllerSocket {

    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("/add")
    public String addSale(@RequestBody PurchaseOrder sale) {
        // هنا يتم نشر بيانات المبيعات الجديدة
        template.convertAndSend("/topic/purchase", sale);
        return "added!";
    }
}

