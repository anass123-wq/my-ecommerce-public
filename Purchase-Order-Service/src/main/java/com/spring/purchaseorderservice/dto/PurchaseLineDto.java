package com.spring.purchaseorderservice.dto;

import com.spring.purchaseorderservice.model.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PurchaseLineDto {
    private Long id;
    private int quantity;
    private double price;

    private PurchaseOrder purchaseOrder;

    private Long productId;
}
