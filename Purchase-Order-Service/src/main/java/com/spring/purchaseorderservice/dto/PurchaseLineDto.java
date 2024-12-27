package com.spring.purchaseorderservice.dto;

import com.spring.purchaseorderservice.model.PurchaseOrder;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PurchaseLineDto {
    private int quantity;
    private double price;
    private Long productId;
}
