package com.spring.purchaseorderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PurchaseLine {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private int quantity;
        private double price;

        @ManyToOne
        @JoinColumn(name = "purchase_order_id")
        private PurchaseOrder purchaseOrder;

        private Long productId;

        // Getters and Setters
}
