package com.spring.salesorderservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LineReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private int quantity;
    private double price;
    private Long productId;
    private Date returnDate;
    private String returnReason;
    @ManyToOne
    @JoinColumn(name = "sales_order_id")
    @JsonBackReference
    private SalesOrder salesOrder;
}
